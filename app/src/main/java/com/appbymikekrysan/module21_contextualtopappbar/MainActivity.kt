package com.appbymikekrysan.module21_contextualtopappbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //2. Теперь нужно создать экземпляр класса ActionMode, мы его сделаем nullable, чтобы ему можно было присваивать значение null для его дезактивации
    private var actionMode: ActionMode? = null  //(3)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //1.Зададим нашему объекту(text_view - Hello world) слушателя, чтобы у нас контесктное меню появлялось по долгому нажатию:
        text_view.setOnLongClickListener {
        //3. Далее для нашего слушателя делаем проверку:
            if(actionMode != null) { //Если создан объект и в данный момент показываются какие-то действия  (4)
                return@setOnLongClickListener false
            }

            actionMode = startActionMode(object: ActionMode.Callback {  //Поскольку при создании actionMode равен нулю, мы его создаем с помощью метода startActionMode присваеваем ему логику действия с ним
                override fun onActionItemClicked(p0: ActionMode?, p1: MenuItem?): Boolean { //В методе onActionItemClicked мы обрабатываем нажатие нашего меню (6)
                    when (p1?.itemId) { //в цикле when по itemId ищем, какая из иконок была нажата
                        R.id.photo -> Toast.makeText(this@MainActivity, "Photo", Toast.LENGTH_SHORT).show() //если мы нажимаем на иконку фото, ты выводится тост с текстом "фото"
                        R.id.android -> Toast.makeText(this@MainActivity, "Android", Toast.LENGTH_SHORT).show() //аналогично
                    }
                    return true
                }

                override fun onCreateActionMode(p0: ActionMode?, p1: Menu?): Boolean {  //в методе onCreateActionMode мы создаем наше меню. Делается это с помощью класа ActionMode, он задан здесь переменной p0
                    val inflater = p0?.menuInflater //то-есть p0-это menuInflatter, мы его присвоили
                    inflater?.inflate(R.menu.our_menu, p1)  //и у inflatter инфлейтим наше меню, которое мы создали в папке menu. Вторым параметром передаем меню ActionMode    (5)
                    p0?.title = "Select option" //Также можно задать заголовок, делается через ActionMode.title и присваиваем string с любым текстом, который нам нужен
                    return true
                }

                override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
                    return false
                }

                override fun onDestroyActionMode(p0: ActionMode?) { //метод onDestroyActionMode понадобится, когда выводится фокус из item
                    actionMode = null //  и происходит дальнейшая логика при дальнейших долгих нажатиях
                }

            })  //Поскольку наш actionMode равен нулю, тогда нам необходимо его создать. Создается он при помощи метода startActionMode

            return@setOnLongClickListener true
        }
    }
}

/*
Для создания Contextual top app bar:
1 Необходим элемент, на котором мы все это будем делать (text_view)
2. Мы задаем обработчик нажатием по нему (setOnLongClickListener)
3. Необходимо создать экземпляр класса ActionMode. Делаем его nullable
4. И обрабатываем экземпляр в слушателе
5. Нужно создать наше меню с item-ами в методе onCreateActionMode
6. И в методе onActionItemClicked мы обрабатываем нажатие по элементам нашего созданного меню
7. B методе onDestroyActionMode мы actionMode обратно возвращаем значение null
 */