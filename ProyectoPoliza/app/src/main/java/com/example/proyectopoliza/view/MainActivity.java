package com.example.proyectopoliza.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectopoliza.R;
import com.example.proyectopoliza.controller.PolicyCalculator;
import com.example.proyectopoliza.controller.PolicyManager;
import com.example.proyectopoliza.controller.UserManager;
import com.example.proyectopoliza.model.Usuario;

public class MainActivity extends AppCompatActivity {

    private TextView textViewUsuarioNombre, textViewCostoPoliza;
    private Spinner spinnerPlan;
    private Button buttonCalcularPoliza, buttonGuardarPoliza;
    private PolicyManager policyManager;
    private UserManager userManager;
    private int usuarioId;
    private String usuarioNombre;
    private CheckBox checkBoxIncendio, checkBoxRobo, checkBoxTerremoto, checkBoxInundacion;
    private ImageView imageViewMenuUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewUsuarioNombre = findViewById(R.id.textViewUsuarioNombre);
        textViewCostoPoliza = findViewById(R.id.textViewCostoPoliza);
        spinnerPlan = findViewById(R.id.spinnerPlan);
        buttonCalcularPoliza = findViewById(R.id.buttonCalcularPoliza);
        buttonGuardarPoliza = findViewById(R.id.buttonGuardarPoliza);
        checkBoxIncendio = findViewById(R.id.checkBoxIncendio);
        checkBoxRobo = findViewById(R.id.checkBoxRobo);
        checkBoxTerremoto = findViewById(R.id.checkBoxTerremoto);
        checkBoxInundacion = findViewById(R.id.checkBoxInundacion);
        imageViewMenuUsuario = findViewById(R.id.imageViewMenuUsuario);

        policyManager = new PolicyManager(this);
        userManager = new UserManager(this);

        usuarioId = getIntent().getIntExtra("USER_ID", -1);
        usuarioNombre = getIntent().getStringExtra("USER_NAME");

        if (usuarioId != -1 && usuarioNombre != null) {
            textViewUsuarioNombre.setText("Usuario: " + usuarioNombre);
        } else {
            // Manejar error si no se recibe el ID o nombre del usuario
            Toast.makeText(this, "Error al cargar la información del usuario.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


        // Configurar Spinner de Planes
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planes_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlan.setAdapter(adapter);

        buttonCalcularPoliza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String planSeleccionado = spinnerPlan.getSelectedItem().toString();
                boolean incendio = checkBoxIncendio.isChecked();
                boolean robo = checkBoxRobo.isChecked();
                boolean terremoto = checkBoxTerremoto.isChecked();
                boolean inundacion = checkBoxInundacion.isChecked();

                double costo = PolicyCalculator.calcularCostoPoliza(planSeleccionado, incendio, robo, terremoto, inundacion);
                textViewCostoPoliza.setText("Costo de la póliza: $" + String.format("%.2f", costo));
            }
        });

        buttonGuardarPoliza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String planSeleccionado = spinnerPlan.getSelectedItem().toString();
                boolean incendio = checkBoxIncendio.isChecked();
                boolean robo = checkBoxRobo.isChecked();
                boolean terremoto = checkBoxTerremoto.isChecked();
                boolean inundacion = checkBoxInundacion.isChecked();
                double costo = PolicyCalculator.calcularCostoPoliza(planSeleccionado, incendio, robo, terremoto, inundacion);

                long polizaId = policyManager.crearPoliza(usuarioId, planSeleccionado, incendio, robo, terremoto, inundacion);
                if (polizaId != -1) {
                    Toast.makeText(MainActivity.this, "Póliza guardada con éxito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error al guardar la póliza", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageViewMenuUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarPopupMenuUsuario(v);
            }
        });
    }

    private void mostrarPopupMenuUsuario(View view) {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_usuario, popupMenu.getMenu()); // Crea el archivo menu_usuario.xml (Paso 5)

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_ver_polizas) {
                    Intent intent = new Intent(MainActivity.this, ListaPolizasActivity.class);
                    intent.putExtra("USER_ID", usuarioId);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.menu_editar_usuario) {
                    mostrarDialogoEditarUsuario();
                    return true;
                } else if (itemId == R.id.menu_eliminar_usuario) {
                    mostrarDialogoEliminarUsuario();
                    return true;
                } else if (itemId == R.id.menu_cerrar_sesion) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }


    private void mostrarDialogoEditarUsuario() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editar Usuario");

        View viewInflated = getLayoutInflater().inflate(R.layout.dialog_editar_usuario, null);
        final EditText inputNombre = (EditText) viewInflated.findViewById(R.id.editTextNuevoNombre);
        final EditText inputPassword = (EditText) viewInflated.findViewById(R.id.editTextNuevaPassword);
        builder.setView(viewInflated);

        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nuevoNombre = inputNombre.getText().toString();
                String nuevaPassword = inputPassword.getText().toString();

                if (!nuevoNombre.isEmpty() || !nuevaPassword.isEmpty()) {
                    String nombreActualizar = nuevoNombre.isEmpty() ? usuarioNombre : nuevoNombre;
                    String passwordActualizar = nuevaPassword.isEmpty() ? "" : nuevaPassword; // Mantener la contraseña actual si no se ingresa una nueva.  En producción, gestionar mejor la contraseña.

                    if (userManager.actualizarUsuario(usuarioId, nombreActualizar, passwordActualizar)) {
                        Toast.makeText(MainActivity.this, "Usuario actualizado", Toast.LENGTH_SHORT).show();
                        textViewUsuarioNombre.setText("Bienvenido, " + nombreActualizar); // Actualizar el nombre en la UI
                        usuarioNombre = nombreActualizar; // Actualizar la variable local
                    } else {
                        Toast.makeText(MainActivity.this, "Error al actualizar usuario", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Debes modificar al menos un campo", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void mostrarDialogoEliminarUsuario() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar Usuario");
        builder.setMessage("¿Estás seguro de que deseas eliminar tu cuenta? Esta acción es irreversible.");

        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (userManager.eliminarUsuario(usuarioId)) {
                    Toast.makeText(MainActivity.this, "Usuario eliminado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Error al eliminar usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}