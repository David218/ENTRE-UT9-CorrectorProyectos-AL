
package vistacontrolador;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modelo.AlumnoNoExistenteExcepcion;
import modelo.CorrectorProyectos;
import modelo.Proyecto;

/**
 * 
 * @author David García
 *
 */
public class GuiCorrectorProyectos extends Application {

	private MenuItem itemLeer;
	private MenuItem itemGuardar;
	private MenuItem itemSalir;

	private TextField txtAlumno;
	private Button btnVerProyecto;

	private RadioButton rbtAprobados;
	private RadioButton rbtOrdenados;
	private Button btnMostrar;

	private TextArea areaTexto;

	private Button btnClear;
	private Button btnSalir;

	private CorrectorProyectos corrector; // el modelo

	@Override
	public void start(Stage stage) {

		corrector = new CorrectorProyectos();

		BorderPane root = crearGui();

		Scene scene = new Scene(root, 800, 600);
		stage.setScene(scene);
		stage.setTitle("- Corrector de proyectos -");
		scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
		stage.show();
	}

	private BorderPane crearGui() {

		BorderPane panel = new BorderPane();
		MenuBar barraMenu = crearBarraMenu();
		panel.setTop(barraMenu);

		VBox panelPrincipal = crearPanelPrincipal();
		panel.setCenter(panelPrincipal);

		HBox panelBotones = crearPanelBotones();
		panel.setBottom(panelBotones);

		return panel;
	}

	private MenuBar crearBarraMenu() {

		MenuBar barraMenu = new MenuBar();

		Menu menu = new Menu("Archivo");

		itemLeer = new MenuItem("_Leer de fichero");
		itemLeer.setAccelerator(KeyCombination.keyCombination("CTRL+L"));
		// a completar
		itemLeer.setOnAction(event -> leerDeFichero());

		itemGuardar = new MenuItem("_Guardar en fichero");
		itemGuardar.setAccelerator(KeyCombination.keyCombination("CTRL+G"));
		itemGuardar.setOnAction(event -> salvarEnFichero());
		itemGuardar.setDisable(true);

		itemSalir = new MenuItem("_Salir");
		itemSalir.setAccelerator(KeyCombination.keyCombination("CTRL+S"));
		itemSalir.setOnAction(event -> salir());

		barraMenu.getMenus().add(menu);

		menu.getItems().addAll(itemLeer, itemGuardar, itemSalir);

		return barraMenu;
	}

	private VBox crearPanelPrincipal() {

		VBox panel = new VBox();
		panel.setPadding(new Insets(5));
		panel.setSpacing(10);

		Label lblEntrada = new Label("Panel de entrada");
		// a completar
		lblEntrada.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
		lblEntrada.getStyleClass().add("titulo-panel");

		HBox panelEntrada = crearPanelEntrada();

		Label lblOpciones = new Label("Panel de opciones");
		lblOpciones.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
		lblOpciones.getStyleClass().add("titulo-panel");

		HBox panelOpciones = crearPanelOpciones();

		areaTexto = new TextArea();
		areaTexto.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
		panel.setVgrow(areaTexto, Priority.ALWAYS);

		panel.getChildren().addAll(lblEntrada, panelEntrada, lblOpciones, panelOpciones, areaTexto);

		return panel;
	}

	private HBox crearPanelEntrada() {

		HBox panel = new HBox();
		panel.setPadding(new Insets(5));
		// a completar
		panel.setSpacing(10);
		Label lblAlumno = new Label("Alumno");

		txtAlumno = new TextField();
		txtAlumno.setPrefColumnCount(30);

		btnVerProyecto = new Button("Ver proyecto");
		btnVerProyecto.setPrefWidth(120);
		btnVerProyecto.setOnAction(event -> verProyecto());
		btnVerProyecto.setDefaultButton(true);

		panel.getChildren().addAll(lblAlumno, txtAlumno, btnVerProyecto);

		return panel;
	}

	private HBox crearPanelOpciones() {

		HBox panel = new HBox();
		panel.setPadding(new Insets(5));
		// a completar
		panel.setSpacing(50);
		panel.setAlignment(Pos.CENTER);

		rbtAprobados = new RadioButton("Mostrar Aprobados");
		rbtAprobados.setSelected(true);

		rbtOrdenados = new RadioButton("Mostrar Ordenados");
		
		ToggleGroup grupo = new ToggleGroup();
		rbtAprobados.setToggleGroup(grupo);
		rbtOrdenados.setToggleGroup(grupo);


		btnMostrar = new Button("Mostrar");
		btnMostrar.setPrefWidth(120);
		btnMostrar.setOnAction(event -> mostrar());

		panel.getChildren().addAll(rbtAprobados, rbtOrdenados, btnMostrar);
		return panel;
	}

	private HBox crearPanelBotones() {

		HBox panel = new HBox();
		panel.setPadding(new Insets(5));
		// a completar
		panel.setSpacing(10);
		panel.setAlignment(Pos.BASELINE_RIGHT);

		btnClear = new Button("Clear");
		btnClear.setPrefWidth(90);
		btnClear.setOnAction(event -> clear());

		btnSalir = new Button("Salir");
		btnSalir.setPrefWidth(90);
		btnSalir.setOnAction(event -> salir());

		panel.getChildren().addAll(btnClear, btnSalir);

		return panel;
	}

	private void salvarEnFichero() {
		// a completar
		try {
			corrector.guardarOrdenadosPorNota();
			areaTexto.setText("Guardados en fichero de texto los proyectos ordenados");
		} catch (IOException e) {
			areaTexto.setText("Error al guardar en el fichero");
		}

	}

	private void leerDeFichero() {
		// a completar
		String mensaje = "Leído fichero de texto\n\n";
		corrector.leerDatosProyectos();
		itemLeer.setDisable(true);
		itemGuardar.setDisable(false);
		List<String> errores = corrector.getErrores();
		mensaje += errores.toString();
		mensaje += "\n\nYa están guardados en memoria los datos de los proyectos";
		areaTexto.setText(mensaje);

	}

	private void verProyecto() {
		// a completar

		if (!itemLeer.isDisable()) {
			areaTexto.setText("No se han leído todavía los datos del fichero\r\n" + "Vaya a la opción leer del menú");

		} else {
			if (txtAlumno.getText().isEmpty()) {
				areaTexto.setText("Teclee nombre de alumno");
			} else {
				try {
					areaTexto.setText(corrector.proyectoDe(txtAlumno.getText()).toString());
				} catch (AlumnoNoExistenteExcepcion e) {
					areaTexto.setText("Alumno/a no existente");
				}
				finally {
					cogerFoco();
				}
			}

		}
		cogerFoco();
	}

	private void mostrar() {

		clear();
		// a completar
		if (!itemLeer.isDisable()) {
			areaTexto.setText("No se han leído todavía los datos del fichero\r\n" + "Vaya a la opción leer del menú");

		}
		else
        {
            if (rbtAprobados.isSelected())
            {
                areaTexto.setText("Han aprobado el proyecto " + corrector.aprobados() + " alumnos/as");
            }
            else if (rbtOrdenados.isSelected())
            {
            	List<Map.Entry<String, Proyecto>> lista = corrector.ordenadosPorNota();
            	Iterator<Map.Entry<String,Proyecto>> it = lista.iterator();
            	String mensaje = "";
            	while(it.hasNext()) {
            		Map.Entry<String,Proyecto> entrada = it.next();
            		mensaje += String.format("%23s",entrada.getKey()) + "\n";
            		mensaje += entrada.getValue().toString() + "\n";
            	}
            	areaTexto.setText(mensaje);
            }
        }
		cogerFoco();

	}

	private void cogerFoco() {

		txtAlumno.requestFocus();
		txtAlumno.selectAll();

	}

	private void salir() {

		System.exit(0);
	}

	private void clear() {

		areaTexto.clear();
		cogerFoco();
	}

	public static void main(String[] args) {

		launch(args);
	}
}
