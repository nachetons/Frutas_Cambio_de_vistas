package com.tema4.tema4ejemplo1.Activities;

import android.media.Image;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tema4.tema4ejemplo1.Adapter.FruitAdapter;
import com.tema4.tema4ejemplo1.Models.Fruit;
import com.tema4.tema4ejemplo2.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    // Clases privadas, en las cuales se declará las listas, clases y variables necesarias para el
    // funcionamiento del proyecto requerido

    private ListView listView;
    private List<Fruit> names;
    private GridView gridView;
    private int counter = 0;
    private MenuItem itemListView;
    private MenuItem itemGridView;
    private FruitAdapter fruitAdapterList;
    private FruitAdapter fruitAdapterGrid;
    static int SWITCH_TO_LIST_VIEW = 0;
    static int SWITCH_TO_GRID_VIEW = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Se relaciona la clase fruta con una nueva variable llamada "names".
        this.names = getallfrura();

        //Se indican las el grid y la lista, donde se va a visualizar la frutaa, y se relaciona con
        //su id correspondiente. Las Ids se pueden ver en el código de la vista del main (es listView
        // y gridView).
        listView = (ListView) findViewById(R.id.listView);
        gridView = (GridView) findViewById(R.id.gridView);

        this.listView.setOnItemClickListener(this);
        this.gridView.setOnItemClickListener(this);

        // Se declara la vcariable "fruitAdapterList" y "fruitAdapterGrid", que es una clase que esta dentro de la carpeta
        // "Adapter". Esta clase se llama "FruitAdapter".
        this.fruitAdapterList = new FruitAdapter(this, R.layout.list_item, names);
        this.fruitAdapterGrid = new FruitAdapter(this, R.layout.grid_item, names);

        //Función para adaptar los objetos, según como se visualice la informacilon
        this.listView.setAdapter(fruitAdapterList);
        this.gridView.setAdapter(fruitAdapterGrid);

        // Se registra dentro del contenido que haya en estas dos variables (las frutas).
        registerForContextMenu(this.listView);
        registerForContextMenu(this.gridView);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_apples);
    }

    // Datos a mostrar


    //Adaptador, la forma visual en que se mostrarán los datos

    @Override

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        this.clickFruit(names.get(position));
    }




    private void clickFruit(Fruit fruit) {
        if (fruit.getOrigin().equals("Unknown")) {
            Toast.makeText(this, "Lo siento no se encontro la fruta pulsada " + fruit.getName(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "La fruta es de " + fruit.getOrigin() + ".Y es una " + fruit.getName(), Toast.LENGTH_SHORT).show();
        }

    }



    private List<Fruit> getallfrura() {
        ArrayList<Fruit> names = new ArrayList();
        names.add(new Fruit("Banana", R.mipmap.ic_banana, "Gran Canaria"));
        names.add(new Fruit("Cereza", R.mipmap.ic_cherry, "Madrid"));
        names.add(new Fruit("Naranja", R.mipmap.ic_orange, "Valencia"));
        names.add(new Fruit("Pera", R.mipmap.ic_pear, "Barcelona"));
        names.add(new Fruit("Ciruela", R.mipmap.ic_plum, "Galicia"));
        names.add(new Fruit("Uvas", R.mipmap.ic_raspberry, "Toledo"));
        names.add(new Fruit("Fresa", R.mipmap.ic_strawberry, "Pais Vasco"));




        return names;
    }

    // Inflamos el layout del menu de opciones
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        this.itemListView = menu.findItem(R.id.list_item);
        this.itemGridView = menu.findItem(R.id.gridView);

        return true;
    }

    // Manejamos eventos click en el menu de opciones
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                // Añadimos nuevo nombre

                this.names.add(new Fruit("Add "+ ++(counter),R.mipmap.ic_apple,"Unknown"));
                // Notificamos al adaptador del cambio producido
                this.fruitAdapterList.notifyDataSetChanged();
                this.fruitAdapterGrid.notifyDataSetChanged();
                return true;
            case R.id.list_item:


                this.switchListGridView(this.SWITCH_TO_LIST_VIEW);
                this.fruitAdapterList.notifyDataSetChanged();
                this.fruitAdapterGrid.notifyDataSetChanged();
                return true;
            case R.id.gridView:
                this.switchListGridView(this.SWITCH_TO_GRID_VIEW);
                this.fruitAdapterList.notifyDataSetChanged();
                this.fruitAdapterGrid.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Inflamos el layout del context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(this.names.get(info.position).getName());
        inflater.inflate(R.menu.context_menu, menu);
    }

    // Manejamos eventos click en el context menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.delete_item:
                // Borramos item clickeado
                this.names.remove(info.position);
                // Notificamos al adaptador del cambio producido
               // this.fruitAdapterList.notifyDataSetChanged();
                this.fruitAdapterList.notifyDataSetChanged();
                this.fruitAdapterGrid.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    private void switchListGridView(int option) {
// Método para cambiar entre Grid/List view
        if (option == SWITCH_TO_LIST_VIEW) {
// Si queremos cambiar a list view, y el list view está en modo invisible...
            if (this.listView.getVisibility() == View.INVISIBLE) {
// ... escondemos el grid view, y enseñamos su botón en el menú de opciones
                this.gridView.setVisibility(View.INVISIBLE);

                this.itemGridView.setVisible(true);
// no olvidamos enseñar el list view, y esconder su botón en el menú de opciones
                this.listView.setVisibility(View.VISIBLE);
                this.itemListView.setVisible(false);
            }
        } else if (option == SWITCH_TO_GRID_VIEW) {
// Si queremos cambiar a grid view, y el grid view está en modo invisible...
            if (this.gridView.getVisibility() == View.INVISIBLE) {
// ... escondemos el list view, y enseñamos su botón en el menú de opciones
                this.listView.setVisibility(View.INVISIBLE);
                this.itemListView.setVisible(true);
// no olvidamos enseñar el grid view, y esconder su botón en el menú de opciones
                this.gridView.setVisibility(View.VISIBLE);
                this.itemGridView.setVisible(false);
            }
        }
    }

}
