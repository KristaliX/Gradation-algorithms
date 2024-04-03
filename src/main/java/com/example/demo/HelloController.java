package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

public class HelloController {

    public javafx.scene.control.Button okButton;
    public javafx.scene.control.TextField logParam;
    public TextField degreeParamC;
    public TextField degreeParamY;
    public Button okButton2;
    @FXML
    private ImageView imageView;


    private Stage stage;
    private File file;

    private Integer logParameter;
    private Double degreeParameterC;
    private Double degreeParameterY;

    @FXML
    protected void openFile() throws FileNotFoundException {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter ex1 = new FileChooser.ExtensionFilter("PNG Image Files", "*.png");
        FileChooser.ExtensionFilter ex3 = new FileChooser.ExtensionFilter("All files", "*.*");
        fileChooser.getExtensionFilters().addAll(ex1, ex3);
        fileChooser.setTitle("Open my files");
        File selectedFile = fileChooser.showOpenDialog(stage);
        file = selectedFile;
        Image image = new Image("file:" + selectedFile.getPath());
        imageView.setImage(image);
    }

    @FXML
    protected void convertToRGB() {
        Image image = new Image("file:" + file.getPath());
        imageView.setImage(image);
    }

    @FXML
    protected void convertToBinary() throws IOException {
        BufferedImage image = ImageIO.read(file);
        BufferedImage result = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D graphic = result.createGraphics();
        graphic.drawImage(image, 0, 0, Color.WHITE, null);
        graphic.dispose();
        File output = new File("binary.png");
        ImageIO.write(result, "png", output);
        Image binary = new Image("file:" + output.getPath());
        imageView.setImage(binary);
    }

    @FXML
    protected void convertToGrayScale() throws IOException {
        BufferedImage image = ImageIO.read(file);
        int width = image.getWidth();
        int height = image.getHeight();
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int p = image.getRGB(x,y);

                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;

                //calculate average
                int avg = (r+g+b)/3;

                //replace RGB value with avg
                p = (a<<24) | (avg<<16) | (avg<<8) | avg;

                image.setRGB(x, y, p);
            }
        }
        File output = new File("grayscale.png");
        ImageIO.write(image, "png", output);
        Image grayScale = new Image("file:" + output.getPath());
        imageView.setImage(grayScale);
    }

    @FXML
    protected void convertToNegative() throws IOException {
        BufferedImage image = ImageIO.read(file);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = image.getRGB(x, y);
                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                // subtract RGB from 255
                r = 255 - r;
                g = 255 - g;
                b = 255 - b;

                // set new RGB value
                p = (a << 24) | (r << 16) | (g << 8) | b;
                image.setRGB(x, y, p);
            }
        }
        File output = new File("negative.png");
        ImageIO.write(image, "png", output);
        Image negative = new Image("file:" + output.getPath());
        imageView.setImage(negative);
    }

    @FXML
    protected void convertWithLogarithm() throws IOException {
        BufferedImage image = ImageIO.read(file);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = image.getRGB(x, y);
                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                // subtract RGB from 255
                r = (int)(logParameter*Math.log(1+r));
                g = (int)(logParameter*Math.log(1+g));
                b = (int)(logParameter*Math.log(1+b));

                // set new RGB value
                p = (a << 24) | (r << 16) | (g << 8) | b;
                image.setRGB(x, y, p);
            }
        }
        File output = new File("logarithm.png");
        ImageIO.write(image, "png", output);
        Image logarithm = new Image("file:" + output.getPath());
        imageView.setImage(logarithm);
    }

    @FXML
    protected void clickOK() {
        String text = logParam.getText();
        logParameter = Integer.parseInt(text);
    }

    @FXML
    protected void convertWithDegree() throws IOException {
        BufferedImage image = ImageIO.read(file);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = image.getRGB(x, y);
                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                // subtract RGB from 255
                r = (int)(degreeParameterC*Math.pow(r, degreeParameterY));
                g = (int)(degreeParameterC*Math.pow(g, degreeParameterY));
                b = (int)(degreeParameterC*Math.pow(b, degreeParameterY));

                // set new RGB value
                p = (a << 24) | (r << 16) | (g << 8) | b;
                image.setRGB(x, y, p);
            }
        }
        File output = new File("degree.png");
        ImageIO.write(image, "png", output);
        Image degree = new Image("file:" + output.getPath());
        imageView.setImage(degree);
    }

    @FXML
    protected void clickOK2() {
        String text = degreeParamC.getText();
        degreeParameterC = Double.parseDouble(text);
        text = degreeParamY.getText();
        degreeParameterY = Double.parseDouble(text);
    }
}