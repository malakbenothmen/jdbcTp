package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ma.projet.service.EtudiantM;


public class ControllerEtudiant {
	@FXML
	private TextField idField ;
	

    @FXML
    private TextField prenomField;
    @FXML
    private TextField nomField;
    @FXML
    private RadioButton sexeFieldF;
    @FXML
    private RadioButton sexeFieldM;
    
    @FXML
    private ComboBox<String> filiereFiield;

    @FXML
    private Button addBtn;
    @FXML
    private Button editBtn;
    @FXML
    private Button deleteBtn;

    @FXML
    private TableView<etudiant> table;
    @FXML
    private TableColumn<etudiant, Integer> idCol;
    @FXML
    private TableColumn<etudiant, String> prenomCol;
    @FXML
    private TableColumn<etudiant, String> nomCol;
    @FXML
    private TableColumn<etudiant, String> sexeCol;
    @FXML
    private TableColumn<etudiant, String> filiereCol;

    private EtudiantM etudiantManager;
    private ObservableList<etudiant> etudiantList;



    public ControllerEtudiant() {
    	etudiantManager = new EtudiantM();

    }
    
    @FXML
    public void initialize() {
        
        etudiantList = FXCollections.observableArrayList(etudiantManager.findAll());
        filiereFiield.getItems().addAll(getListFields());
        sexeFieldF.setSelected(false);
        filiereFiield.setPromptText("DSI");
        filiereFiield.setValue("DSI");
        
              
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        sexeCol.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        filiereCol.setCellValueFactory(new PropertyValueFactory<>("filiere"));

        table.setItems(etudiantList);
        
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                prenomField.setText(newSelection.getPrenom());
                nomField.setText(newSelection.getNom());
                if (newSelection.getSexe().equals("F")) {
                    sexeFieldF.setSelected(true);
                } else {
                    sexeFieldM.setSelected(true);
                }
                filiereFiield.setPromptText(newSelection.getFiliere());
                filiereFiield.setValue(newSelection.getFiliere());
                
            } else {
                clearFields();
            }
        });


   
    }

    @FXML

    private List<String> getListFields() {
        String[] fields = {"DSI","RSI", "MDW","SEM"}; 
        List<String> ls = new ArrayList<>();
        for(String f : fields)
        {ls.add(f);}
        return ls ;
    }



    
    
    
    @FXML
    private void addBtnAction() {
    	 String prenom = prenomField.getText();
         String nom = nomField.getText();
         String sexe = sexeFieldF.isSelected() ? "F" : "M";
         String filiere = filiereFiield.getValue();


        if (nom.isEmpty() || prenom.isEmpty() || sexe.isEmpty() ) {
            showAlert("Error", "Please fill all fields.",Alert.AlertType.ERROR);
            return;
        }

        etudiant newEtudiant = new etudiant(nom, prenom, sexe, filiere);
        boolean isSuccess = etudiantManager.create(newEtudiant);
        if (isSuccess) {
        	etudiantList.clear();
            etudiantList.addAll(etudiantManager.findAll());
        	showAlert("Succes", "ajout d'etudiant successfully .",Alert.AlertType.INFORMATION);
            clearFields();
        } else {
            showAlert("Erreur", "Impossible d'ajouter l'étudiant.",Alert.AlertType.ERROR);
        }
    }
    
    
    @FXML
    private void editBtnAction() {
        etudiant selectedEtudiant = table.getSelectionModel().getSelectedItem();
        if (selectedEtudiant == null) {
            showAlert("Erreur", "Veuillez sélectionner un étudiant à éditer.",Alert.AlertType.ERROR);
            return;
        }

        // Récupérer les nouvelles informations de l'étudiant
        String prenom = prenomField.getText();
        String nom = nomField.getText();
        String sexe = sexeFieldF.isSelected() ? "F" : "M";
        String filiere = filiereFiield.getValue();

        // Mettre à jour uniquement les informations modifiées
        selectedEtudiant.setPrenom(prenom);
        selectedEtudiant.setNom(nom);
        selectedEtudiant.setSexe(sexe);
        selectedEtudiant.setFiliere(filiere);

        boolean isSuccess = etudiantManager.update(selectedEtudiant);

        if (isSuccess) {
            etudiantList.setAll(etudiantManager.findAll());
            showAlert("Succès", "L'étudiant a été mis à jour avec succès.", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Erreur", "Impossible de mettre à jour l'étudiant.",Alert.AlertType.ERROR);
        }
    }


    @FXML
    private void deleteBtnAction() {
        etudiant selectedEtudiant = table.getSelectionModel().getSelectedItem();
        if (selectedEtudiant == null) {
            showAlert("Erreur", "Veuillez sélectionner un étudiant à supprimer.",Alert.AlertType.ERROR);
            return;
        }

        
        int id = selectedEtudiant.getId();

       
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Suppression de l'étudiant");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cet étudiant ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            etudiant etud = etudiantManager.findById(id);
            boolean isSuccess = etudiantManager.delete(etud);

            if (isSuccess) {
                etudiantList.setAll(etudiantManager.findAll());
                showAlert("Succès", "L'étudiant a été supprimé avec succès.",Alert.AlertType.INFORMATION);
                clearFields();
            } else {
                showAlert("Erreur", "Impossible de supprimer l'étudiant.",Alert.AlertType.ERROR);
            }
        }
    }





 

    private void clearFields() {
        prenomField.clear();
        nomField.clear();
        sexeFieldF.setSelected(false);
        sexeFieldM.setSelected(false);
        filiereFiield.setValue("DSI"); // dsi comme valeur par defaut 
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
