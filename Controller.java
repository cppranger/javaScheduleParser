package sample;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javafx.scene.control.Hyperlink;
import javafx.scene.layout.TilePane;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.io.File;


import java.io.File;
import java.util.ResourceBundle;

public class Controller {

    static String destination = "e:/scheduleVyatsu/temp_scheduleVyatsu.pdf";
    static File pdf = new File("e:/scheduleVyatsu/scheduleVyatsu.pdf");
    //ObservableList<String> groups = FXCollections.observableArrayList("ЛВб-4301", "ЛВб-4201", "ЛВб-4202", "ПОДб-4203");


    //groupsComboBox.setValue("ЛВб-4202");


    //groupsComboBox.setOnAction(event -> lbl.setText(groupsComboBox.getValue()));

    @FXML
    private Button checkSchedule;

    @FXML
    private ComboBox<String> getGroup;

    @FXML
    private Hyperlink link2me;

    public Controller() throws IOException {
    }

    @FXML
    void initialize() {
        getGroup.getItems().removeAll(getGroup.getItems());
        getGroup.getItems().addAll("ЛВб-3201", "ЛВб-3202", "ЛВб-3203", "ЛВб-3204",
                "ЛВб-4201", "ЛВб-4202", "ЛВб-4204",
                "ПОДб-4203", "ПОДб-4204");
        //getGroup.getSelectionModel().select(Integer.parseInt("ЛВб-4202"));

        link2me.setOnAction(event -> {
                launchBrowser("www.instagram.com/cpp_ranger/");
        });

        checkSchedule.setOnAction(event -> {
            try {
                String group;
                String check = getGroup.getValue();

                if (check == "ЛВб-3202") {
                    group = "listPeriod_144801";
                    parser(group);
                }

                else if (check == "ЛВб-3203") {
                    group = "listPeriod_144811";
                    parser(group);
                }

                else if (check == "ЛВб-3204") {
                    group = "listPeriod_144791";
                    parser(group);
                }

                else if (check == "ЛВб-4204") {
                    group = "listPeriod_143861";
                    parser(group);
                }

                else if (check == "ЛВб-4201") {
                    group = "listPeriod_143841";
                    parser(group);
                }

                else if (check == "ЛВб-4202") {
                    group = "listPeriod_143851";
                    parser(group);
                }

                else if (check == "ЛВб-3201") {
                    group = "listPeriod_144781";
                    parser(group);
                }

                else if (check == "ПОДб-4203") {
                    group = "listPeriod_143831";
                    parser(group);
                }

                else if (check == "ПОДб-4204") {
                    group = "listPeriod_143821";
                    parser(group);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    void parser(String group) throws IOException {
        Document doc = Jsoup.connect("https://www.vyatsu.ru/studentu-1/spravochnaya-informatsiya/raspisanie-zanyatiy-dlya-studentov.html").get();
        String content = doc.getElementById(group).outerHtml();
        String clean_url = null;
        for (int i = 0; i < 150; i++) {
            if (i > 95 && i < 149) clean_url += "" + content.charAt(i);
        }
        assert clean_url != null;
        StringBuilder strbuff = new StringBuilder(clean_url);
        strbuff.delete(0, 4);
        clean_url = strbuff.toString();
        clean_url = "https://www.vyatsu.ru" + clean_url;
        System.out.println("div : " + clean_url);
        try {
            // downloading
            downloadUsingStream(clean_url, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File temp_pdf = new File("e:/scheduleVyatsu/temp_scheduleVyatsu.pdf");

        Desktop desktop = null;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
        }

        if (pdf.exists()) {
            if (pdf.length() == temp_pdf.length()) {
                System.out.println("У Вас последняя версия расписания!");
                temp_pdf.delete();
                try {
                    desktop.open(new File(String.valueOf(pdf)));
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
            else {
                System.out.println("Загружаю новое расписание!");
                pdf.delete();
                temp_pdf.renameTo(new File("e:/scheduleVyatsu/scheduleVyatsu.pdf"));
                temp_pdf.delete();
                try {
                    desktop.open(new File(String.valueOf(pdf)));
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        else {
            System.out.println("Загружаю новое расписание!");
            temp_pdf.renameTo(new File("e:/scheduleVyatsu/scheduleVyatsu.pdf"));
            temp_pdf.delete();
            try {
                desktop.open(new File(String.valueOf(pdf)));
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    private static void downloadUsingStream(String urlStr, String file) throws IOException{
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count=0;
        while((count = bis.read(buffer,0,1024)) != -1)
        {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
    }

    private void launchBrowser(String uriStr) {
        Desktop desktop;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                // launch browser
                URI uri;
                try {
                    uri = new URI("http://" + uriStr);
                    desktop.browse(uri);
                }
                catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                catch (URISyntaxException use) {
                    use.printStackTrace();
                }
            }
        }
    }
}
