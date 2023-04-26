import javax.swing.JPanel; // Importa la clase JPanel de Swing
import javax.swing.JSlider; // Importa la clase JSlider de Swing
import javax.swing.event.ChangeEvent; // Importa la clase ChangeEvent de Swing
import javax.swing.event.ChangeListener; // Importa la clase ChangeListener de Swing

import java.awt.Color; // Importa la clase Color de AWT
import java.awt.Font; // Importa la clase Font de AWT
import java.awt.GradientPaint;
import java.awt.Graphics; // Importa la clase Graphics de AWT
import java.awt.Graphics2D; // Importa la clase Graphics2D de AWT
import java.awt.event.ActionEvent; // Importa la clase ActionEvent de AWT
import java.awt.event.ActionListener; // Importa la clase ActionListener de AWT
import java.awt.event.MouseEvent; // Importa la clase MouseEvent de AWT
import java.awt.event.MouseListener; // Importa la clase MouseListener de AWT
import java.awt.image.BufferedImage; // Importa la clase BufferedImage de AWT
import java.awt.Dimension; //Libreria para especificar las dimensiones de un componente gráfico
import javax.swing.JButton; // Importa la clase JButton de Swing
import javax.swing.JFrame; // Importa la clase JFrame de Swing
import javax.swing.JLabel; // Importa la clase JLabel de Swing
import javax.swing.BorderFactory; //Libreria para crear y personalizar bordes para componentes gráficos
import javax.imageio.ImageIO; // Importa la clase ImageIO de Java

import javax.swing.JFileChooser; // Importa la clase JFileChooser de Swing
import javax.swing.filechooser.FileNameExtensionFilter; // Importa la clase FileNameExtensionFilter de Swing
import java.io.File; // Importa la clase File de Java
import java.io.IOException; // Importa la clase IOException de Java


//Clase MyWindow que hereda de JFrame e implementa ActionListener, MouseListener y ChangeListener
public class MyWindow extends JFrame implements ActionListener, MouseListener, ChangeListener {
	

//Declaración de componentes y variables
JPanel contentPane; // Panel principal
Canvas canvas; // Lienzo
JLabel lbl; // Etiqueta
JSlider one; // Control deslizante (no utilizado en el programa)
static Graphics g; // Objeto gráfico
static BufferedImage img; // Imagen en búfer
int width, height, x, y; // Dimensiones y coordenadas

ColorPalette colorPalette; // Paleta de colores preseleccionados
JPanel selectedColorPanel; // Panel que muestra el color seleccionado
JSlider redSlider, greenSlider, blueSlider; // Controles deslizantes para seleccionar el color
JLabel createArtLabel, selectedColorLabel; // Etiquetas para mostrar mensajes
JButton gridColorButton1, gridColorButton2, backgroundColorButton1, backgroundColorButton2; // Botones para seleccionar colores de cuadrícula y fondo
JButton saveButton, loadButton, clearButton; // Botones para guardar, cargar y borrar

//Constructor de MyWindow
public MyWindow(int width, int height) {
	
 this.width = width; // Inicializa el ancho
 this.height = height; // Inicializa la altura
 
 components(); // Llama al método components() para configurar los componentes
 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Establece la operación de cierre por defecto
 setSize(width, height); // Establece el tamaño del marco
 setLocationRelativeTo(null); // Centra el marco en la pantalla
 setLayout(null); // Establece el diseño del marco como nulo
 setTitle("My Drawing App"); // Establece el título del marco
 setVisible(true); // Hace visible el marco
 }
            
//Método para configurar y agregar componentes al panel principal
private void components() {
 contentPane = new JPanel(); // Crea un nuevo panel principal
 lbl = new JLabel(""); // Crea una etiqueta vacía
 canvas = new Canvas(); // Crea un área de dibujo
 one = new JSlider(0, 100); // Crea un control deslizante con rango de 0 a 100

 // Crea botón para guardar la imagen, establece su posición y tamaño, y agrega un ActionListener
 saveButton = new JButton("Guardar");
 saveButton.setBounds(canvas.getX(), canvas.getY() + canvas.getHeight() + 100, 100, 30);
 saveButton.addActionListener(new SaveImageListener());

 // Crea botón para cargar la imagen, establece su posición y tamaño, y agrega un ActionListener
 loadButton = new JButton("Cargar");
 loadButton.setBounds(saveButton.getX() + saveButton.getWidth() + 10, saveButton.getY(), 100, 30);
 loadButton.addActionListener(new LoadImageListener());

 // Crea botón para borrar el área de dibujo, establece su posición y tamaño, y agrega un ActionListener
 clearButton = new JButton("Borrar");
 clearButton.setBounds(loadButton.getX() + loadButton.getWidth() + 10, loadButton.getY(), 100, 30);
 clearButton.addActionListener(new ClearCanvasListener());

 contentPane = new JPanel() {
	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);

	        Graphics2D g2d = (Graphics2D) g;
	        int width = getWidth();
	        int height = getHeight();

	        Color color1 = new Color(230, 230, 230); // Gris muy claro
	        Color color2 = new Color(200, 200, 200); // Gris claro
	        Color color3 = new Color(150, 150, 150); // Gris medio
	        Color color4 = new Color(100, 100, 100); // Gris oscuro

	        int numSections = 3;
	        int sectionHeight = height / numSections;

	        GradientPaint gp1 = new GradientPaint(0, 0, color1, 0, sectionHeight, color2);
	        GradientPaint gp2 = new GradientPaint(0, sectionHeight, color2, 0, 2 * sectionHeight, color3);
	        GradientPaint gp3 = new GradientPaint(0, 2 * sectionHeight, color3, 0, 3 * sectionHeight, color4);

	        g2d.setPaint(gp1);
	        g2d.fillRect(0, 0, width, sectionHeight);

	        g2d.setPaint(gp2);
	        g2d.fillRect(0, sectionHeight, width, 2 * sectionHeight);

	        g2d.setPaint(gp3);
	        g2d.fillRect(0, 2 * sectionHeight, width, 3 * sectionHeight);
	    }
	};
	contentPane.setLayout(null);
	contentPane.setBounds(0, 0, width, height);

 lbl.setBounds(0, 0, width, height); // Establece las dimensiones de la etiqueta
 lbl.setForeground(Color.white); // Establece el color del texto de la etiqueta
 lbl.setFont(new Font("Serif", Font.PLAIN, 70)); // Establece la fuente de la etiqueta

 one.setBounds(0, 0, 335, 35); // Establece las dimensiones del control deslizante
 one.addChangeListener(this); // Agrega un ChangeListener al control deslizante

 canvas.setBounds((width / 2) - 250, (height / 2) - 300, 500, 500); // Establece las dimensiones del área de dibujo

 // Crea una etiqueta con el texto "Crea tu propio arte" y establece sus dimensiones
 createArtLabel = new JLabel("Crea tu propio arte");
 createArtLabel.setBounds(canvas.getX(), canvas.getY() - 20, 200, 20);

 // Crea una etiqueta con el texto "Color elegido" y establece sus dimensiones
 selectedColorLabel = new JLabel("Color elegido");
 selectedColorLabel.setBounds(canvas.getX() + canvas.getWidth() + 20, canvas.getY() + canvas.getHeight() + 30, 150, 20);

 // Crea una paleta de colores con un ActionListener personalizado y establece sus dimensiones
 colorPalette = new ColorPalette(new ColorSelectionListener(), 4, 4);
 colorPalette.setBounds(canvas.getX() + canvas.getWidth() + 20, canvas.getY() + 50, 170, 250);

//Crea un botón para seleccionar el color de fondo negro y agrega un ActionListener
        backgroundColorButton1 = createColorButton(Color.BLACK, canvas.getX() + canvas.getWidth() + 50, colorPalette.getY() + colorPalette.getHeight() + 100);
        backgroundColorButton1.addActionListener(new BackgroundColorSelectionListener());
     // Crea un botón para seleccionar el color de fondo blanco y agrega un ActionListener
       
        backgroundColorButton2 = createColorButton(Color.WHITE, backgroundColorButton1.getX() + backgroundColorButton1.getWidth() + 10, backgroundColorButton1.getY());
        backgroundColorButton2.addActionListener(new BackgroundColorSelectionListener());
        

        // Crea un panel para mostrar el color seleccionado y establece sus dimensiones
        selectedColorPanel = new JPanel();
        selectedColorPanel.setBackground(Color.WHITE);
        selectedColorPanel.setBounds(canvas.getX() + canvas.getWidth() + 20, canvas.getY() + canvas.getHeight() + 60, 100, 50);

        // Crea un control deslizante de color rojo y establece sus dimensiones
        redSlider = createColorSlider(Color.RED, canvas.getX() + canvas.getWidth() / 2 - 150, canvas.getY() + canvas.getHeight() + 8);
        // Crea un control deslizante de color verde y establece sus dimensiones
        greenSlider = createColorSlider(Color.GREEN, redSlider.getX(), redSlider.getY() + redSlider.getHeight() + 18);
        // Crea un control deslizante de color azul y establece sus dimensiones
        blueSlider = createColorSlider(Color.BLUE, greenSlider.getX(), greenSlider.getY() + greenSlider.getHeight() + 21);

        // Agrega un MouseListener al área de dibujo
        canvas.addMouseListener(this);

     // Agrega todos los componentes al panel principal
        contentPane.add(one); // Añade el primer componente (one) al panel de contenido
        contentPane.add(canvas); // Añade el área de dibujo (canvas) al panel de contenido
        contentPane.add(lbl); // Añade la etiqueta (lbl) al panel de contenido
        contentPane.add(colorPalette); // Añade la paleta de colores (colorPalette) al panel de contenido
        contentPane.add(selectedColorPanel); // Añade el panel de color seleccionado (selectedColorPanel) al panel de contenido
        contentPane.add(redSlider); // Añade el control deslizante de rojo (redSlider) al panel de contenido
        contentPane.add(greenSlider); // Añade el control deslizante de verde (greenSlider) al panel de contenido
        contentPane.add(blueSlider); // Añade el control deslizante de azul (blueSlider) al panel de contenido
        contentPane.add(createArtLabel); // Añade la etiqueta "Crear arte" (createArtLabel) al panel de contenido
        contentPane.add(selectedColorLabel); // Añade la etiqueta de color seleccionado (selectedColorLabel) al panel de contenido
        contentPane.add(backgroundColorButton1); // Añade el botón de color de fondo 1 (backgroundColorButton1) al panel de contenido
        contentPane.add(backgroundColorButton2); // Añade el botón de color de fondo 2 (backgroundColorButton2) al panel de contenido
        contentPane.add(saveButton); // Añade el botón de guardar (saveButton) al panel de contenido
        contentPane.add(loadButton); // Añade el botón de cargar (loadButton) al panel de contenido
        contentPane.add(clearButton); // Añade el botón de limpiar (clearButton) al panel de contenido

        // Agrega el panel principal a la ventana
        add(contentPane);
    }

//Método para crear controles deslizantes de color personalizados
private JSlider createColorSlider(Color color, int x, int y) {
 JSlider slider = new JSlider(0, 255); // Crea un nuevo control deslizante (slider) con un rango de 0 a 255
 slider.setPaintTicks(false); // No muestra marcas de graduación en el control deslizante
 slider.setPaintLabels(false); // No muestra etiquetas en el control deslizante
 slider.setMajorTickSpacing(50); // Establece el espaciado entre marcas principales en 50
 slider.setMinorTickSpacing(10); // Establece el espaciado entre marcas secundarias en 10
 slider.setForeground(color); // Establece el color de primer plano del control deslizante al color especificado
 slider.setBackground(color); // Establece el color de fondo del control deslizante al color especificado
 slider.setBounds(x, y, 300, 30); // Establece la posición y el tamaño del control deslizante
 slider.addChangeListener(new ChangeListener() { // Añade un detector de cambios al control deslizante
     @Override
     public void stateChanged(ChangeEvent e) { // Método que se llama cuando cambia el estado del control deslizante
         updateSelectedColorFromSliders(); // Actualiza el color seleccionado según los valores de los controles deslizantes
     }
 });
 return slider; // Devuelve el control deslizante creado
}

    // Método para actualizar el color seleccionado en función de los valores de los controles deslizantes de color
    private void updateSelectedColorFromSliders() {
        Color selectedColor = new Color(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue());
        selectedColorPanel.setBackground(selectedColor);
        canvas.setColor(selectedColor);
    }

    // Método que se ejecuta cuando se produce un evento de acción
    public void actionPerformed(ActionEvent event) {
        lbl.setText("HOLA TODOS !!!");
    }
 // Método que se ejecuta cuando se hace clic en el área de dibujo
    @Override
    public void mouseClicked(MouseEvent e) {

        // Obtiene las coordenadas X e Y del clic
        x = e.getX();
        y = e.getY();

        // Establece el texto de la etiqueta 'lbl' con las coordenadas del clic
        lbl.setText(x + " " + y);
    }
    
    
    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    // Método que se ejecuta cuando se cambia el valor del control deslizante 'one'
    @Override
    public void stateChanged(ChangeEvent e) {
        lbl.setText("val : " + one.getValue());
    }

    // Método para crear botones de color
    private JButton createColorButton(Color color, int x, int y) {
        JButton button = new JButton(); // Crea un nuevo botón
        button.setBackground(color); // Establece el color de fondo del botón al color especificado
        button.setBounds(x, y, 30, 30); // Establece la posición y el tamaño del botón
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Establece el borde del botón con un borde negro
        button.setPreferredSize(new Dimension(30, 30)); // Establece las dimensiones preferidas del botón
        return button; // Devuelve el botón creado
    }
    

    // Clase interna para manejar la selección de colores de fondo
    private class BackgroundColorSelectionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource(); // Obtiene el botón que generó el evento
            Color selectedColor = source.getBackground(); // Obtiene el color de fondo del botón
            canvas.setBackground(selectedColor); // Establece el color de fondo del lienzo al color seleccionado
        }
    }

    // Clase interna para manejar la selección de colores de la paleta
    private class ColorSelectionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource(); // Obtiene el botón que generó el evento
            Color selectedColor = source.getBackground(); // Obtiene el color de fondo del botón
            canvas.setColor(selectedColor); // Establece el color de dibujo del lienzo al color seleccionado
            selectedColorPanel.setBackground(selectedColor); // Establece el color de fondo del panel de color seleccionado
            redSlider.setValue(selectedColor.getRed()); // Establece el valor del control deslizante de rojo al valor de rojo del color seleccionado
            greenSlider.setValue(selectedColor.getGreen()); // Establece el valor del control deslizante de verde al valor de verde del color seleccionado
            blueSlider.setValue(selectedColor.getBlue()); // Establece el valor del control deslizante de azul al valor de azul del color seleccionado
        }
    }

    
    private class SaveImageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar imagen como");
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop/"));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, GIF & PNG Images", "jpg", "gif", "png");
            fileChooser.setFileFilter(filter);

            int returnVal = fileChooser.showSaveDialog(MyWindow.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String filePath = file.getAbsolutePath();

                String[] extensions = {"jpg", "gif", "png"};

                for (String extension : extensions) {
                    File outputFile = new File(filePath + "." + extension);

                    try {
                        BufferedImage imgToSave = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
                        Graphics2D g2d = imgToSave.createGraphics();

                        g2d.setColor(canvas.getBackground());
                        g2d.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                        g2d.drawImage(canvas.getImg(), 0, 0, null);

                        // Dibuja la cuadrícula
                        g2d.setColor(Color.GRAY);
                        for (int row = 0; row < canvas.ROWS; row++) {
                            for (int col = 0; col < canvas.COLS; col++) {
                                g2d.drawRect(row * canvas.CELL_SIZE, col * canvas.CELL_SIZE, canvas.CELL_SIZE, canvas.CELL_SIZE);
                            }
                        }

                        ImageIO.write(imgToSave, extension, outputFile);
                        g2d.dispose();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
    // Clase interna para manejar la acción de cargar la imagen
    private class LoadImageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser(); // Crea un nuevo selector de archivos
            FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, GIF & PNG Images", "jpg", "gif", "png"); // Crea un filtro para archivos de imagen
            fileChooser.setFileFilter(filter); // Establece el filtro de archivos en el selector de archivos
            int returnVal = fileChooser.showOpenDialog(MyWindow.this); // Muestra el cuadro de diálogo de abrir archivo y guarda el resultado
            if (returnVal == JFileChooser.APPROVE_OPTION) { // Si el usuario aprueba la selección de archivos
                File file = fileChooser.getSelectedFile(); // Obtiene el archivo seleccionado
                try {
                    BufferedImage image = ImageIO.read(file); // Lee la imagen del archivo
                    canvas.setImage(image); // Establece la imagen en el lienzo
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    // Clase interna para manejar la acción de borrar el área de dibujo
    private class ClearCanvasListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            canvas.clear(); // Limpia el área de dibujo en el lienzo
        }
    }

    } // Fin de la clase