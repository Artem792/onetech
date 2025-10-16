package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat

class ShopActivity : AppCompatActivity() {

    private lateinit var aiAssistantButton: Button
    private lateinit var categoriesGrid: GridView
    private lateinit var navCatalog: LinearLayout
    private lateinit var navCart: LinearLayout
    private lateinit var navProfile: LinearLayout

    private val categories = listOf(
        Category("💻", "Готовые ПК", "pcs"),
        Category("⚡", "Процессоры", "cpu"),
        Category("🎮", "Видеокарты", "gpu"),
        Category("🧠", "Память", "ram"),
        Category("💾", "Накопители", "storage"),
        Category("🔌", "Блоки питания", "psu"),
        Category("📦", "Корпуса", "cases"),
        Category("❄️", "Охлаждение", "cooling"),
        Category("🔋", "Материнские платы", "motherboards")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        initViews()
        setupCategoriesGrid()
        setupClickListeners()
    }

    private fun initViews() {
        aiAssistantButton = findViewById(R.id.aiAssistantButton)
        categoriesGrid = findViewById(R.id.categoriesGrid)
        navCatalog = findViewById(R.id.navCatalog)
        navCart = findViewById(R.id.navCart)
        navProfile = findViewById(R.id.navProfile)
    }

    private fun setupCategoriesGrid() {
        val adapter = object : ArrayAdapter<Category>(this, R.layout.item_category, categories) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: layoutInflater.inflate(R.layout.item_category, parent, false)
                val category = getItem(position)!!

                val iconText = view.findViewById<TextView>(R.id.categoryIcon)
                val nameText = view.findViewById<TextView>(R.id.categoryName)
                val card = view.findViewById<LinearLayout>(R.id.categoryCard)

                iconText.text = category.icon
                nameText.text = category.name

                card.setOnClickListener {
                    openCategory(category.id)
                }

                return view
            }
        }

        categoriesGrid.adapter = adapter
    }

    private fun setupClickListeners() {
        aiAssistantButton.setOnClickListener {
            Toast.makeText(this, "Открывается ИИ-помощник для подбора ПК...", Toast.LENGTH_SHORT).show()
        }

        navCatalog.setOnClickListener {
            setActiveNavItem(navCatalog)
            Toast.makeText(this, "Каталог", Toast.LENGTH_SHORT).show()
        }
        navCart.setOnClickListener {
            setActiveNavItem(navCart)
            Toast.makeText(this, "Корзина", Toast.LENGTH_SHORT).show()
        }
        navProfile.setOnClickListener {
            setActiveNavItem(navProfile)
            Toast.makeText(this, "Профиль", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setActiveNavItem(activeItem: LinearLayout) {
        val navItems = listOf(navCatalog, navCart, navProfile)
        navItems.forEach { item ->
            val textView = item.getChildAt(1) as TextView
            textView.setTextColor(0x80FFFFFF.toInt()) // Серый цвет
        }

        val activeTextView = activeItem.getChildAt(1) as TextView
        activeTextView.setTextColor(0xFF4169E1.toInt()) // Синий цвет
    }

    private fun openCategory(categoryId: String) {
        Toast.makeText(this, "Открывается категория: $categoryId", Toast.LENGTH_SHORT).show()
    }

    data class Category(val icon: String, val name: String, val id: String)
}