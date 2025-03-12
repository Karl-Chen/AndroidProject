using GoodStore.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace GoodStore.Controllers
{
    public class ProductListController : Controller
    {
        private readonly GoodStoreContext _context;

        public ProductListController(GoodStoreContext context)
        {
            _context = context;
        }

        public async Task<IActionResult> Index(string? CateID)
        {

            //select * from product

            var products = _context.Product.Include(p => p.Cate).AsQueryable();
            if (!string.IsNullOrEmpty(CateID))
            {
                products = products.Where(p => p.CateID == CateID);
            }

            ViewData["Cate"] = await _context.Category.ToListAsync();

            return View(await products.ToListAsync());
        }

        public IActionResult Cart()
        {
            return View();
        }
    }
}
