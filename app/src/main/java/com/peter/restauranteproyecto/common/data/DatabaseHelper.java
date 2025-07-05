package com.peter.restauranteproyecto.common.data;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.peter.restauranteproyecto.common.model.Mesa;
import com.peter.restauranteproyecto.common.model.Pedido;
import com.peter.restauranteproyecto.common.model.Reserva;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "restaurante.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Empleado (nombre TEXT PRIMARY KEY, fechaIngreso TEXT, rol TEXT, email TEXT, telefono TEXT, estado TEXT, turno TEXT, horario TEXT, rendimiento REAL, salario REAL)");

        db.execSQL("CREATE TABLE Ingredientes (nombre TEXT PRIMARY KEY, categoria TEXT, stockActual TEXT, stockMinimo TEXT, estado TEXT, proveedor TEXT, costoUnidad REAL, fechaReposicion TEXT)");

        db.execSQL("CREATE TABLE Mesa (nombre TEXT PRIMARY KEY, capacidad INTEGER, estado TEXT, mesero TEXT, clientes TEXT, informacion TEXT)");

        db.execSQL("CREATE TABLE Plato (nombre TEXT PRIMARY KEY, descripcion TEXT, categoria TEXT, precio REAL, estado TEXT, tiempoPrep INTEGER)");

        db.execSQL("CREATE TABLE Pedido (id TEXT PRIMARY KEY, mesa TEXT, articulos TEXT, estado TEXT, horaPedido TEXT, estimado TEXT, total REAL, prioridad TEXT, mesero TEXT)");

        db.execSQL("CREATE TABLE Proveedor (nombre TEXT PRIMARY KEY, productos TEXT, categoria TEXT, telefono TEXT, correo TEXT, direccion TEXT, calificacion REAL, estado TEXT, totalPedidos INTEGER, ultimaEntrega TEXT, tiempoEntrega TEXT)");
        db.execSQL("CREATE TABLE Reserva (" +
                "codigo TEXT PRIMARY KEY," +
                "cliente TEXT," +
                "fecha TEXT," +
                "hora TEXT," +
                "comensales INTEGER," +
                "mesa TEXT," +
                "estado TEXT," +
                "solicitudes TEXT," +
                "mesero TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS CierreCaja (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "total_boletas INTEGER, " +
                "importe_total REAL, " +
                "fecha TEXT)");

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Empleado");
        db.execSQL("DROP TABLE IF EXISTS Ingredientes");
        db.execSQL("DROP TABLE IF EXISTS Mesa");
        db.execSQL("DROP TABLE IF EXISTS Plato");
        db.execSQL("DROP TABLE IF EXISTS Pedido");
        db.execSQL("DROP TABLE IF EXISTS Proveedor");
        db.execSQL("DROP TABLE IF EXISTS Reserva");
        onCreate(db);
    }
    public boolean insertarReserva(Reserva reserva) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("codigo", reserva.getCodigo());
        values.put("cliente", reserva.getCliente());
        values.put("fecha", reserva.getFecha());
        values.put("hora", reserva.getHora());
        values.put("comensales", reserva.getComensales());
        values.put("mesa", reserva.getMesa());
        values.put("estado", reserva.getEstado());
        values.put("solicitudes", reserva.getSolicitudes());
        values.put("mesero", reserva.getMesero());

        long result = db.insert("Reserva", null, values);
        return result != -1;
    }
    public List<Reserva> obtenerTodasLasReservas() {
        List<Reserva> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Reserva", null);

        if (cursor.moveToFirst()) {
            do {
                String codigo = cursor.getString(cursor.getColumnIndexOrThrow("codigo"));
                String cliente = cursor.getString(cursor.getColumnIndexOrThrow("cliente"));
                String fecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"));
                String hora = cursor.getString(cursor.getColumnIndexOrThrow("hora"));
                int comensales = cursor.getInt(cursor.getColumnIndexOrThrow("comensales"));
                String mesa = cursor.getString(cursor.getColumnIndexOrThrow("mesa"));
                String estado = cursor.getString(cursor.getColumnIndexOrThrow("estado"));
                String solicitudes = cursor.getString(cursor.getColumnIndexOrThrow("solicitudes"));
                String mesero = cursor.getString(cursor.getColumnIndexOrThrow("mesero"));

                Reserva reserva = new Reserva(codigo, cliente, fecha, hora, comensales, mesa, estado, solicitudes, mesero);
                lista.add(reserva);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return lista;
    }

    public boolean actualizarEstadoReserva(String codigo, String nuevoEstado) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("estado", nuevoEstado);
        int result = db.update("Reserva", values, "codigo = ?", new String[]{codigo});
        return result > 0;
    }

    public boolean eliminarReservaPorCodigo(String codigo) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("Reserva", "codigo = ?", new String[]{codigo});
        return result > 0;
    }


    // ====== PEDIDOS ======


    public List<Pedido> obtenerTodosLosPedidos() {
        List<Pedido> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Pedido", null);

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                String mesa = cursor.getString(cursor.getColumnIndexOrThrow("mesa"));
                String articulosJson = cursor.getString(cursor.getColumnIndexOrThrow("articulos"));
                String estado = cursor.getString(cursor.getColumnIndexOrThrow("estado"));
                String hora = cursor.getString(cursor.getColumnIndexOrThrow("horaPedido"));
                String estimado = cursor.getString(cursor.getColumnIndexOrThrow("estimado"));
                double total = cursor.getDouble(cursor.getColumnIndexOrThrow("total"));
                String prioridad = cursor.getString(cursor.getColumnIndexOrThrow("prioridad"));
                String mesero = cursor.getString(cursor.getColumnIndexOrThrow("mesero"));

                List<String> articulos = new Gson().fromJson(articulosJson, new TypeToken<List<String>>(){}.getType());

                Pedido p = new Pedido(id, mesa, articulos, estado, hora, estimado, total, prioridad, mesero);
                lista.add(p);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lista;
    }

    public void actualizarEstadoPedido(String idPedido, String nuevoEstado) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("estado", nuevoEstado);
        db.update("Pedido", values, "id = ?", new String[]{idPedido});
        db.close();
    }

    public void eliminarPedido(String idPedido) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Pedido", "id = ?", new String[]{idPedido});
        db.close();
    }

    //MESAS
    public void insertarPedido(Pedido pedido) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Insertar el pedido
        ContentValues pedidoValues = new ContentValues();
        pedidoValues.put("id", pedido.getId());
        pedidoValues.put("mesa", pedido.getMesa());
        pedidoValues.put("articulos", new Gson().toJson(pedido.getArticulos()));
        pedidoValues.put("estado", pedido.getEstado());
        pedidoValues.put("horaPedido", pedido.getHoraPedido());
        pedidoValues.put("estimado", pedido.getEstimado());
        pedidoValues.put("total", pedido.getTotal());
        pedidoValues.put("prioridad", pedido.getPrioridad());
        pedidoValues.put("mesero", pedido.getMesero());

        db.insert("Pedido", null, pedidoValues);

        String nombreMesa = pedido.getMesa().replaceAll("[^0-9]", "");
        Mesa mesa = new Mesa(
                nombreMesa,
                0,
                "Ocupada",
                pedido.getMesero(),
                "",
                "Pedido: " + pedido.getHoraPedido()
        );
        insertarOModificarMesa(mesa);

        db.close();
    }




    public List<Mesa> obtenerTodasLasMesas() {
        List<Mesa> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Mesa", null);

        if (cursor.moveToFirst()) {
            do {
                Mesa mesa = new Mesa(
                        cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("capacidad")),
                        cursor.getString(cursor.getColumnIndexOrThrow("estado")),
                        cursor.getString(cursor.getColumnIndexOrThrow("mesero")),
                        cursor.getString(cursor.getColumnIndexOrThrow("clientes")),
                        cursor.getString(cursor.getColumnIndexOrThrow("informacion"))
                );
                lista.add(mesa);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lista;
    }
    public void actualizarEstadoMesa(String nombreMesa, String nuevoEstado) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("estado", nuevoEstado);
        db.update("Mesa", values, "nombre = ?", new String[]{nombreMesa});
        db.close();
    }
    public void insertarOModificarMesa(Mesa mesa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", mesa.getNombre());
        values.put("capacidad", mesa.getCapacidad());
        values.put("estado", mesa.getEstado());
        values.put("mesero", mesa.getMesero());
        values.put("clientes", mesa.getClientes());
        values.put("informacion", mesa.getInformacion());

        db.insertWithOnConflict("Mesa", null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void eliminarMesasConPrefijoMesa() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Mesa", "nombre LIKE 'Mesa %'", null);
        db.close();

    }

    public void limpiarMesasInconsistentes() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM Mesa WHERE nombre LIKE 'Mesa %'");  // elimina "Mesa 4", "Mesa 5", etc.
        db.close();
    }
    // CAJA
    public void insertarCierreCaja(int totalBoletas, double importeTotal, String fecha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("total_boletas", totalBoletas);
        values.put("importe_total", importeTotal);
        values.put("fecha", fecha);
        db.insert("CierreCaja", null, values);
        db.close();
    }

    public List<String> obtenerHistorialCierres() {
        List<String> historial = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CierreCaja ORDER BY id DESC", null);

        if (cursor.moveToFirst()) {
            do {
                String resumen = "📅 Fecha: " + cursor.getString(cursor.getColumnIndexOrThrow("fecha")) +
                        " | 🧾 Boletas: " + cursor.getInt(cursor.getColumnIndexOrThrow("total_boletas")) +
                        " | 💰 Total: S/ " + String.format("%.2f", cursor.getDouble(cursor.getColumnIndexOrThrow("importe_total")));
                historial.add(resumen);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return historial;
    }


}



