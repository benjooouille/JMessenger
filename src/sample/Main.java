package sample;

import java.sql.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Main extends Application {

    static String pseudo;
    static int numeroLigneSalon;

    public static void debug () {

        //sql("ALTER TABLE utilisateurs ADD salon VARCHAR(50);");
        sql("UPDATE utilisateurs SET salon = 'Les BRO' WHERE pseudo = 'Benjo';");

    }

    public static void sql(String instruction) {
        try{

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Jmessenger?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC","root","root");

            Statement stmt = con.createStatement();

            stmt.executeUpdate(instruction);

            con.close();
        }catch(Exception e){ System.out.println(e);}

    }

    public static boolean rechercheUtilisateur (String pseudoSaisi, String mdpSaisi) {
        try{

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Jmessenger?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC","root","root");

            Statement stmt = con.createStatement();

            ResultSet rs=stmt.executeQuery("select * from utilisateurs");

            boolean utilisateurExistant = false;

            while(rs.next()) {

                if (pseudoSaisi.equals(rs.getString(1))) {
                    if (mdpSaisi.equals(rs.getString(2))) {
                        utilisateurExistant = true;
                    }

                }

            }

            con.close();

            return utilisateurExistant;


        }catch(Exception e){ System.out.println(e); return false;}

    }

    public static ArrayList<String> getSalons (String pseudo) {
        ArrayList<String> salons = new ArrayList<String>();
        try{

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Jmessenger?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC","root","root");

            Statement stmt = con.createStatement();

            ResultSet rs=stmt.executeQuery("select * from utilisateurs");

            while(rs.next()) {
                System.out.println(rs.getString(1));
                if (rs.getString(1).equals(pseudo)) {
                    System.out.println("Pseudo trouve");
                    ResultSetMetaData rsmd = rs.getMetaData();

                    int columnsNumber = rsmd.getColumnCount();
                    System.out.println("nombre de colonnes : "+columnsNumber);
                    int colonne = 4;

                    while(colonne <= columnsNumber) {
                        System.out.println(rs.getString(colonne));
                        salons.add(rs.getString(colonne));
                        colonne ++;
                    }
                    return salons;

                }

            }

            con.close();

        }catch(Exception e){
            System.out.println(e);

        }
        return salons;
    }

    public static int getNombreSalons (String pseudo) {

        try{

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Jmessenger?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC","root","root");

            Statement stmt = con.createStatement();

            ResultSet rs=stmt.executeQuery("select * from utilisateurs");

            while(rs.next()) {

                if (rs.getString(1).equals(pseudo)) {

                    int nombreSalons = rs.getInt(3);

                    return nombreSalons;

                }

            }

            con.close();

        }catch(Exception e){
            System.out.println(e);

        }
        return 0;
    }

    public static int getNombreMembres (String salon) {

        try{

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Jmessenger?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC","root","root");

            Statement stmt = con.createStatement();

            ResultSet rs=stmt.executeQuery("select * from salons");

            while(rs.next()) {

                if (rs.getString(1).equals(salon)) {

                    int nombreMembres = rs.getInt(3);

                    return nombreMembres;

                }

            }

            con.close();

        }catch(Exception e){
            System.out.println(e);

        }
        return 0;
    }


    @Override
    public void start(Stage pageConnexion) {

        //Initialisation du titre de la page connexion
        pageConnexion.setTitle("JMessenger");

        //Initialisation de la grille pour la page connexion
        GridPane grilleConnexion = new GridPane();
        grilleConnexion.setAlignment(Pos.CENTER);
        grilleConnexion.setHgap(10);
        grilleConnexion.setVgap(10);
        grilleConnexion.setPadding(new Insets(25, 25, 25, 25));

        /* INITIALISATION DES DIFFERENTS ELEMENTS DE LA PAGE DE CONNEXION */

        //Message de bienvenue
        Text titreSceneConnexion = new Text("Bienvenue");
        titreSceneConnexion.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grilleConnexion.add(titreSceneConnexion, 0, 0, 2, 1);

        //Pseudo
        Label textePseudo = new Label("Pseudo:");
        grilleConnexion.add(textePseudo, 0, 1);

        //Saisie du pseudo
        TextField saisiePseudo = new TextField();
        grilleConnexion.add(saisiePseudo, 1, 1);

        //Mot de passe
        Label texteMdp = new Label("Mot de passe:");
        grilleConnexion.add(texteMdp, 0, 2);

        //Saisie du mot de passe
        PasswordField saisieMdp = new PasswordField();
        grilleConnexion.add(saisieMdp, 1, 2);

        Button connexion = new Button("Connexion");
        grilleConnexion.add(connexion, 0, 4);

        Button inscription = new Button("Inscription");
        grilleConnexion.add(inscription, 1, 4);

        //Initialisation de la scene de la page connexion
        Scene sceneConnexion = new Scene(grilleConnexion, 300, 200);

        //Initialisation de la page principale
        Stage pagePrincipale = new Stage();
        pagePrincipale.setTitle("JMessenger");
        GridPane grillePrincipale = new GridPane();
        grillePrincipale.setAlignment(Pos.TOP_LEFT);
        grillePrincipale.setHgap(10);
        grillePrincipale.setVgap(10);
        grillePrincipale.setPadding(new Insets(25, 25, 25, 25));

        //Bouton deconnexion
        Button boutonDeconnexion = new Button ("Déconnexion");
        grillePrincipale.add(boutonDeconnexion, 5, 0);

        Button boutonCreerSalon = new Button("Créer un salon");
        grillePrincipale.add(boutonCreerSalon, 0, 2);

        /* CREATION D'UN SALON */

        //Nom du salon
        Label texteNomSalonCreation = new Label("Nom du salon:");
        grillePrincipale.add(texteNomSalonCreation,0,2);

        //Saisie du nom du salon
        TextField saisieNomSalonCreation = new TextField();
        grillePrincipale.add(saisieNomSalonCreation, 1, 2);

        Button boutonCreer = new Button("Créer");
        grillePrincipale.add(boutonCreer, 2, 2);

        saisieNomSalonCreation.setVisible(false);
        texteNomSalonCreation.setVisible(false);
        boutonCreer.setVisible(false);

        //Salons disponibles
        Label texteSalons = new Label("Salons: ");
        grillePrincipale.add(texteSalons,0,3);

        Scene scenePrincipale = new Scene(grillePrincipale, 800, 500);

        connexion.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                pseudo = saisiePseudo.getText();
                String mdp = saisieMdp.getText();
                Utilisateur utilisateur = new Utilisateur(pseudo, mdp);
                if(rechercheUtilisateur(pseudo, mdp)) {

                    Text scenetitle = new Text("Bonjour "+pseudo);
                    scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
                    grillePrincipale.add(scenetitle, 0, 0, 2, 1);

                    //Récupération des salons de l'utilisateur dans la base de données
                    ArrayList<String> salons = getSalons(pseudo);

                    //Parcours des salons
                    for(int i = 0; i<salons.size(); i++ ){

                        numeroLigneSalon = i + 4;
                        String nomSalon = salons.get(i);

                        if(nomSalon != null) {

                            //Création d'un bouton sur la page principale
                            Button boutonSalon = new Button(nomSalon);
                            grillePrincipale.add(boutonSalon, 0, numeroLigneSalon);

                            //Initialisation de la page du salon
                            Stage pageSalon = new Stage();
                            pageSalon.setTitle(nomSalon);

                            GridPane grilleSalon = new GridPane();
                            //grilleSalon.setAlignment(Pos.TOP_LEFT);
                            grilleSalon.setHgap(15);
                            grilleSalon.setVgap(1);

                            grilleSalon.setPadding(new Insets(25, 0, 0, 0));

                            Text texteNomSalon = new Text(nomSalon);
                            texteNomSalon.setFont(Font.font ("Verdana", 20));
                            grilleSalon.add(texteNomSalon, 1, 0);

                            Button boutonVoirMembres = new Button("Voir membres");
                            grilleSalon.add(boutonVoirMembres, 1, 3);

                            Button boutonAjouterMembre = new Button("Ajouter un membre");
                            grilleSalon.add(boutonAjouterMembre, 2, 3);

                            Text textePseudoMembre = new Text("Pseudo du membre à ajouter:");
                            grilleSalon.add(textePseudoMembre, 2, 3);

                            TextField saisiePseudoMembre = new TextField();
                            grilleSalon.add(saisiePseudoMembre, 2, 4);

                            Button boutonAjouter = new Button ("Ajouter");
                            grilleSalon.add(boutonAjouter, 2, 5);

                            textePseudoMembre.setVisible(false);
                            saisiePseudoMembre.setVisible(false);
                            boutonAjouter.setVisible(false);

                            Button boutonSupprimerMembre = new Button("Supprimer un membre");
                            grilleSalon.add(boutonSupprimerMembre, 3, 3, 8, 1);

                            Text textePseudoMembreSupprimer = new Text("Pseudo du membre à supprimer:");
                            grilleSalon.add(textePseudoMembreSupprimer, 3, 3, 8, 1);

                            TextField saisiePseudoMembreSupprimer = new TextField();
                            grilleSalon.add(saisiePseudoMembreSupprimer, 3, 4, 8, 1);

                            Button boutonSupprimer = new Button ("Supprimer");
                            grilleSalon.add(boutonSupprimer, 3, 5, 8, 1);

                            textePseudoMembreSupprimer.setVisible(false);
                            saisiePseudoMembreSupprimer.setVisible(false);
                            boutonSupprimer.setVisible(false);

                            TextField saisieMessage = new TextField();
                            //saisieMessage.setMinSize( 3, 30);
                            grilleSalon.add(saisieMessage, 0, 400, 10, 1);

                            Button boutonEnvoyerMessage = new Button("Envoyer");
                            //boutonEnvoyerMessage.setMinSize(50,30);
                            grilleSalon.add(boutonEnvoyerMessage, 10, 400);

                            Scene sceneSalon= new Scene(grilleSalon, 500, 500);
                            pageSalon.setScene(sceneSalon);

                            boutonAjouterMembre.setOnAction(new EventHandler<ActionEvent>() {

                                @Override
                                public void handle(ActionEvent e) {
                                    boutonAjouterMembre.setVisible(false);
                                    textePseudoMembre.setVisible(true);
                                    saisiePseudoMembre.setVisible(true);
                                    boutonAjouter.setVisible(true);
                                }
                            });

                            boutonAjouter.setOnAction(new EventHandler<ActionEvent>() {
                                int numeroLigneMembre = 3;
                                @Override
                                public void handle(ActionEvent e) {
                                    String nomMembre = saisiePseudoMembre.getText();

                                    int nombreMembres = getNombreMembres(nomSalon);
                                    System.out.println(nombreMembres);

                                    sql("UPDATE salons SET membre"+nombreMembres+" = '"+nomMembre+"' WHERE nom = '"+nomSalon+"';");

                                    sql("UPDATE salons SET nombreMembres = "+(nombreMembres+1)+" WHERE nom = '"+nomSalon+"';");

                                    boutonAjouterMembre.setVisible(true);
                                    textePseudoMembre.setVisible(false);
                                    saisiePseudoMembre.setVisible(false);
                                    boutonAjouter.setVisible(false);

                                }
                            });

                            numeroLigneSalon++;

                            boutonSalon.setOnAction(new EventHandler<ActionEvent>() {

                                @Override
                                public void handle(ActionEvent e) {
                                    pageSalon.show();
                                }
                            });
                        }

                    }
                    pagePrincipale.setScene(scenePrincipale);
                    pagePrincipale.show();
                    pageConnexion.close();

                }
                else {
                    final Text actiontarget = new Text();
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("Pseudo ou mot de passe incorrect");
                    grilleConnexion.add(actiontarget, 0, 6);
                }
            }
        });

        inscription.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                pseudo = saisiePseudo.getText();
                String mdp = saisieMdp.getText();
                Utilisateur utilisateur = new Utilisateur(pseudo, mdp);
                System.out.println(pseudo);
                System.out.println(mdp);
                //setSql(pseudo, mdp);
                sql("INSERT INTO utilisateurs (pseudo, mdp, nombreSalons) VALUES ('"+pseudo+"','"+mdp+"',0);");
                Text scenetitle = new Text("Bienvenue "+pseudo);
                scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
                grillePrincipale.add(scenetitle, 0, 0, 2, 1);
                pagePrincipale.setScene(scenePrincipale);
                pagePrincipale.show();
                pageConnexion.close();

            }
        });

        boutonDeconnexion.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                pagePrincipale.close();
                pageConnexion.show();

            }
        });

        boutonCreerSalon.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                boutonCreerSalon.setVisible(false);
                saisieNomSalonCreation.setVisible(true);
                texteNomSalonCreation.setVisible(true);
                boutonCreer.setVisible(true);
            }
        });

        boutonCreer.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                boutonCreerSalon.setVisible(true);

                String nomSalonCreation = saisieNomSalonCreation.getText();

                int numeroSalon = getNombreSalons(pseudo);

                //sql("ALTER TABLE utilisateurs ADD salon"+no+" VARCHAR(10);");


                sql("UPDATE utilisateurs SET salon"+numeroSalon+" = '"+nomSalonCreation+"' WHERE pseudo = '"+pseudo+"';");

                sql("INSERT INTO salons (nom, createur, nombreMembres) VALUES ('"+nomSalonCreation+"','"+pseudo+"',1);");

                //sql("UPDATE salons SET createur = '"+pseudo+"' WHERE nom = '"+nomSalonCreation+"';");

                sql("UPDATE utilisateurs SET nombreSalons = "+(numeroSalon+1)+" WHERE pseudo = '"+pseudo+"';");

                //Initialisation de la page du salon
                Stage pageSalon = new Stage();
                pageSalon.setTitle(nomSalonCreation);

                GridPane grilleSalon = new GridPane();
                //grilleSalon.setAlignment(Pos.TOP_LEFT);
                grilleSalon.setHgap(15);
                grilleSalon.setVgap(1);

                grilleSalon.setPadding(new Insets(25, 0, 0, 0));

                Text texteNomSalon = new Text(nomSalonCreation);
                texteNomSalon.setFont(Font.font ("Verdana", 20));
                grilleSalon.add(texteNomSalon, 1, 0);

                Button boutonVoirMembres = new Button("Voir membres");
                grilleSalon.add(boutonVoirMembres, 1, 3);

                Button boutonAjouterMembre = new Button("Ajouter un membre");
                grilleSalon.add(boutonAjouterMembre, 2, 3);

                Text textePseudoMembre = new Text("Pseudo du membre à ajouter:");
                grilleSalon.add(textePseudoMembre, 2, 3);

                TextField saisiePseudoMembre = new TextField();
                grilleSalon.add(saisiePseudoMembre, 2, 4);

                Button boutonAjouter = new Button ("Ajouter");
                grilleSalon.add(boutonAjouter, 2, 5);

                textePseudoMembre.setVisible(false);
                saisiePseudoMembre.setVisible(false);
                boutonAjouter.setVisible(false);

                Button boutonSupprimerMembre = new Button("Supprimer un membre");
                grilleSalon.add(boutonSupprimerMembre, 3, 3, 8, 1);

                Text textePseudoMembreSupprimer = new Text("Pseudo du membre à supprimer:");
                grilleSalon.add(textePseudoMembreSupprimer, 3, 3, 8, 1);

                TextField saisiePseudoMembreSupprimer = new TextField();
                grilleSalon.add(saisiePseudoMembreSupprimer, 3, 4, 8, 1);

                Button boutonSupprimer = new Button ("Supprimer");
                grilleSalon.add(boutonSupprimer, 3, 5, 8, 1);

                textePseudoMembreSupprimer.setVisible(false);
                saisiePseudoMembreSupprimer.setVisible(false);
                boutonSupprimer.setVisible(false);

                TextField saisieMessage = new TextField();
                //saisieMessage.setMinSize( 3, 30);
                grilleSalon.add(saisieMessage, 0, 400, 10, 1);

                Button boutonEnvoyerMessage = new Button("Envoyer");
                //boutonEnvoyerMessage.setMinSize(50,30);
                grilleSalon.add(boutonEnvoyerMessage, 10, 400);

                Scene sceneSalon= new Scene(grilleSalon, 500, 500);
                pageSalon.setScene(sceneSalon);

                boutonVoirMembres.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent e) {
                        Stage pageMembresSalon = new Stage();

                        pageMembresSalon.setTitle("Membres "+nomSalonCreation);
                        GridPane grilleMembresSalon = new GridPane();
                        grilleMembresSalon.setAlignment(Pos.TOP_LEFT);
                        grilleMembresSalon.setHgap(10);
                        grilleMembresSalon.setVgap(10);
                        grilleMembresSalon.setPadding(new Insets(25, 25, 25, 25));
                        Text texteMembres = new Text("Membres "+nomSalonCreation);
                        grilleMembresSalon.add(texteMembres, 0, 0);

                        Scene sceneMembresSalon = new Scene(grilleMembresSalon, 200, 200);
                        pageMembresSalon.setScene(sceneMembresSalon);

                        pageMembresSalon.show();
                    }
                });

                boutonAjouterMembre.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent e) {
                        boutonAjouterMembre.setVisible(false);
                        textePseudoMembre.setVisible(true);
                        saisiePseudoMembre.setVisible(true);
                        boutonAjouter.setVisible(true);
                    }
                });

                boutonAjouter.setOnAction(new EventHandler<ActionEvent>() {
                    int numeroLigneMembre = 3;
                    @Override
                    public void handle(ActionEvent e) {
                        String nomMembre = saisiePseudoMembre.getText();

                        int nombreMembres = getNombreMembres(nomSalonCreation);
                        System.out.println(nombreMembres);

                        sql("UPDATE salons SET membre"+nombreMembres+" = '"+nomMembre+"' WHERE nom = '"+nomSalonCreation+"';");

                        sql("UPDATE salons SET nombreMembres = "+(nombreMembres+1)+" WHERE nom = '"+nomSalonCreation+"';");

                        boutonAjouterMembre.setVisible(true);
                        textePseudoMembre.setVisible(false);
                        saisiePseudoMembre.setVisible(false);
                        boutonAjouter.setVisible(false);


                        /*
                        Text texteNomMembre = new Text(nomMembre);
                        grilleSalon.add(texteNomMembre, 1, numeroLigneMembre);
                        numeroLigneMembre ++;
                        */
                    }
                });


                pageSalon.show();

                saisieNomSalonCreation.setVisible(false);
                texteNomSalonCreation.setVisible(false);
                boutonCreer.setVisible(false);

                Button boutonSalon = new Button(nomSalonCreation);
                grillePrincipale.add(boutonSalon, 0, numeroLigneSalon);

                numeroLigneSalon ++;


            }
        });


        pageConnexion.setScene(sceneConnexion);
        pageConnexion.show();
    }


    public static void main(String[] args) {
        //debug();
        launch(args);

    }
}
