import java.io.FileInputStream;
import java.util.Properties;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;



public class App_IA {
    static Scanner  sc = new Scanner(System.in);
	static Connection con = connectDB();
    public static void main(String[] args) {
        System.out.println("Bienvenu \n Qui etes vous ?");
        System.out.println("1- Administrateur");
        System.out.println("2- Gestionnaire d'ecole");
        System.out.println("3- Eleve ");
        switch (sc.nextInt()) {
            case 1:
                System.out.println("Merci de vous identifier dabord ");
                if(authentification("admin", "passer"))
                //Sous menu d'administration
                System.out.println("1- Gestion des Ecoles");
                System.out.println("2- Gestion des professeurs ");
                System.out.println("3- Gestion des performances");
                System.out.println("4- Gestion des programmes ");
                switch (sc.nextInt()) {
                    case 1:
                    //sous menu de gestion d'ecole
                        System.out.println("1- Ajouter une ecole ");
                        System.out.println("2- Ajouter Un enseignant a une ECOLE");
                        switch (sc.nextInt()) {
                            case 1:
                                ajouterEcole();
                                break;
                            case 2:
                                affecterEnseignant();
                                break;
                            default:
                                System.out.println("Choix indisponible !! Veuillez recommencer ");
                                break;
                        }
                        break;
                    case 2:
                    //Sous menu de gestion des enseignants
                        System.out.println("1- Ajouter un professeur");
                        System.out.println("2- Affecter un Enseignant a une ecole");
                        System.out.println("3- Affecter une matiere a un enseignant ");
                        switch (sc.nextInt()) {
                            case 1:
                                ajouterEnseignant();
                                break;
                            case 2:
                                affecterEnseignant();
                                
                                break;
                            case 3:
                                affecterMatiereEnseignant();
                            default:
                                break;
                        }
                    break;
                    case 4:
                        System.out.println("1-Ajouter une matiere");
                        System.out.println("2-Retirer une matiere ");
                        switch (sc.nextInt()) {
                            case 1:
                                ajouterMatiere();
                                break;
                        
                            default:
                                break;
                        }

                        break;
                    case 3:
                        System.out.println("1- The bests ");
                        System.out.println("2- Par ecole");
                        switch (sc.nextInt()) {
                            case 1:
                                System.out.println("1- Liste des meilleurs performances de la Zone Par Niveau");
                                System.out.println("2- Meilleurs performances de la Zone Par Matiere");
                                switch (sc.nextInt()) {
                                    case 1:
                                    for (int niveauClasse = 6; niveauClasse >2; niveauClasse--) {
                                        System.out.println("\n\n\t\tNIVEAU : "+niveauClasse+"iem\n\n");
                                        afficherBulletins(listeEleves("select * from eleve where classe in (select code from classe where niveau="+niveauClasse+");",false), 3);
                                    }
                                        break;
                                    case 2:
                                    System.out.println("Voici la liste des matieres");
                                    listeMatieres("select * from matieres ;", true);
                                    System.out.println("\n\n\tVeuillez entrer le code de la matiere choisie");
                                    int codeMatiere = sc.nextInt();
                                    for (int niveauClasse = 6; niveauClasse >2; niveauClasse--) {
                                        System.out.println("\n\n\t\tNIVEAU : "+niveauClasse+"iem\n\n");
                                        getNotesElevesMatiere(listeEleves("select * from eleve where classe in (select code from classe where niveau="+niveauClasse+");", false), 3,listeMatieres("select * from matieres where code="+codeMatiere+";", false)[0] , true);
                                    }
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            case 2:
                                System.out.println("Voici la liste des ecoles ");
                                listeEcoles("select * from ecoles;", true);
                                System.out.println("Veuillez selectionner l'ecole ");
                                int codeEcole = sc.nextInt();
                                notesManager(codeEcole);
                            break;
                    
                        default:
                        break;
                        }
                    break;
                }
                break;
            case 2:
                System.out.println("Voici la liste des ecole");
                listeEcoles("select * from ecoles;", true);
                System.out.println("Veuillez choisir votre ecole");
                int codeEcole= sc.nextInt();
                authentification("user", "passer");
                System.out.println("1-Gestion des Classe");
                System.out.println("2-Gestion des Eleves");
                System.out.println("3-Gestion des Notes");
                switch (sc.nextInt()) {
                    case 1:
                        System.out.println("1- Ajouter une CLASSE");
                        System.out.println("2- Affecter une matiere a une CLASSE");
                        System.out.println("3- Retirer une matiere a une CLASSE");
                        switch (sc.nextInt()) {
                            case 1:
                                ajouterClasse(codeEcole);
                                break;
                            case 2:
                                affecterMatiereClasse(codeEcole);
                                break;
                            case 3:
                                
                                break;
                            default:
                                break;
                        }
                    break;
                    case 2:
                        System.out.println("1-Ajouter un eleve ");
                        System.out.println("2-Renvoyer un eleve ");
                        switch (sc.nextInt()) {
                            case 1:
                                ajouterEleve(codeEcole);
                                break;
                            default:
                                break;
                        }
                        break;
                    case 3:
                        System.out.println("1- Ajouter les Notes");
                        System.out.println("2- Consulter les resultats");
                        switch (sc.nextInt()) {
                            case 2:
                                notesManager(codeEcole);
                                break;
                            case 1:
                                notesClasses(codeEcole);
                                break;
                            default:
                                break;
                        }
                    default:
                        break;
                }   
            break;
            case 3:
                System.out.println("Veuillez vous identifier pour consulter votre bulletin");
                Eleve N = null;
                int login;
                do {
                    System.out.print("Login: ");
                    login=sc.nextInt();
                    System.out.print("Password: ");
                    sc.nextLine();
                    sc.nextLine();
                } while ((listeEleves("select * from eleve where tel="+login+";", true).length == 0));
                    getBulletin(N, 1, true);
                    System.out.println("\n1- Voir mon classemnt au niveau de l'ecole\n2- Voir mon classement niveau zonal");
                    if(sc.nextInt() == 1)
                    getBulletin(N, 2, true);
                    else
                    getBulletin(N, 3, true);
                
                default:
                break;
        }
    }
    public static void notesClasses(int codeEcole){
		System.out.println("Voici la liste des classes de votre ecole ");
	    listeClasse("select * from classe where ecole="+codeEcole+";",true);
		System.out.println("Veuillez selectionner la classe ");
		Classe classe = listeClasse("select * from classe where code="+sc.nextInt()+";",false)[0];
		System.out.println("Voici la liste des matieres apprises dans cette classe ");
		do {
            listeMatieres("select * from matieres where code in (select matiere from matiere_classe_prof where classe ="+classe.getCode()+");",true);
		    System.out.println("Veuillez selectionner la matiere");
		    Matiere matiere = listeMatieres("select * from matieres where code="+sc.nextInt()+";",false)[0];
		    NotesClasseMatiere(matiere, classe);
            System.out.println(" 1 pour recommencer ");
        } while (sc.nextInt() == 1);
	}

    public static void NotesClasseMatiere(Matiere matiere, Classe classe) {
		System.out.println("Voici la liste des eleves de la classe "+classe.getNom());
		Eleve eleves [] =	listeEleves("select * from eleve where classe="+classe.getCode()+";",true);
		for (int i = 0; i < eleves.length; i++) 
			notesEleveMatiere(eleves[i],matiere);
	}

    public static void notesEleveMatiere(Eleve eleve,Matiere matiere){
		try {
            Notes N = new Notes();
		N.setEleve(eleve);
		N.setMatiere(matiere);
		System.out.println("Eleve : "+ N.getEleve().getPrenom() + " "+ N.getEleve().getNom() + "\t\t Matiere: " + N.getMatiere().getNom() );
		System.out.println("\n Veuillez entrer la note de CC ");
		N.setNoteCC(sc.nextFloat());
		System.out.println("Veuillez entrer la note de CT ");
		N.setNoteCT(sc.nextFloat());
		N.setMoyenne((N.getNoteCC() + N.getNoteCT() )/2);
        ResultSet s = myInterface("select coef from matiere_classe_prof where classe="+N.getEleve().getClasse().getCode()+" ;",1 );
        s.next();
        N.setCoef(s.getFloat(1));
        System.out.println("Right");
		myInterface("insert into notes(eleve,matiere,noteCC,noteCT,moyenne,coef) values("+(N.getEleve().getCode())+","+(N.getMatiere().getCode())+","+(N.getNoteCC())+","+(N.getNoteCT())+","+(N.getMoyenne())+","+N.getCoef()+");", 2);
		System.out.println("Perfect !! ");
        } catch (Exception e) {
            // TODO: handle exception
        }
	}
    public static Notes [] listeNotes(String request){
		Notes [] N = new Notes [getNumberOfRows(request)];
		ResultSet s = myInterface(request, 1);
		try {
			int i=0;
			while (s.next()) {
				N[i] = new Notes();
				N[i].setEleve(listeEleves("select * from eleve where code="+s.getInt(1)+";",false)[0]);
				N[i].setMatiere(listeMatieres("select * from matieres where code="+s.getInt(2)+";",false)[0]);
				N[i].setNoteCC(s.getInt(3));
				N[i].setNoteCT(s.getInt(4));
				N[i].setMoyenne(s.getFloat(5));
				N[i++].setCoef(s.getInt(6));
			
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return N;
	}

    public static int getRang(Notes N, String requestEnsemble) {
        Notes Ensemble [] =   listeNotes(requestEnsemble);   
        int rang = 1;     
        for (int i = 0; i < Ensemble.length; i++) 
            if(Ensemble[i].getMoyenne() > N.getMoyenne())
                rang++;
        return rang;
    }

public static void ajouterEcole() {
        try {
		Ecole E = new Ecole();
		ResultSet s = myInterface("select count(*) from ecoles;", 1);
		s.next();
		E.setCode((s.getInt(1))+1);
		sc.nextLine();
		System.out.println("\tCode : "+E.getCode()+" \n\nVeuillez entrer le nom de l'ecole :");
		E.setNom(sc.nextLine());
		System.out.println("Voici la liste des travailleurs( Ce qui sont deja directeur ne sont pas presente !! On ne peut peut etre directeur de plusieurs ecoles quand meme !!)");
		System.out.println("Merci de selectionner un directeur pour cette ecole");
		int codi = sc.nextInt();
        myInterface("insert into Ecoles(nom,directeur) values (\""+E.getNom()+"\",\""+ codi+"\");", 2);
       } catch (Exception e) {
        System.out.println("Erreur survenue a la methode ajoutEcole !!");
        e.getMessage();
    }
}
    public static Eleve [] listeEleves(String request, boolean printIt) {
		Eleve eleves [] = new Eleve [getNumberOfRows(request)];
		try {
            if(printIt)
                System.out.println("CODE\tNOM\t\tPRENOM\t\tTelephone\tCLASSE\tECOLE");
			int i= 0;
			ResultSet s = myInterface(request, 1);
			while (s.next()) {
				eleves[i] = new Eleve();
				eleves[i].setCode(s.getInt(1));
				eleves[i].setClasse(listeClasse("select * from classe where code="+s.getInt(2)+";",false)[0]);
				eleves[i].setNom(s.getString(3));
				eleves[i].setPrenom(s.getString(4));
				eleves[i].setTel(s.getString(5));
                if(printIt)
                System.out.println(eleves[i].getCode()+"\t"+eleves[i].getNom()+"\t"+eleves[i].getPrenom()+"\t"+eleves[i].getTel()+"\t"+eleves[i].getClasse().getNom()+"\t"+listeEcoles("select * from ecoles where code in (select ecole from classe where code="+eleves[i].getClasse().getCode()+");",false)[0].getNom());
				i++;
			}
		} catch (Exception e) {

			System.out.println("erreur a listeEleves()");
			e.printStackTrace();
		}
		return eleves;
	}

    public static Classe [] listeClasse(String request, boolean printIt){
		Classe classes []= new Classe [getNumberOfRows(request)];
		try {
            if(printIt)
            System.out.println("CODE \t NOM \t Niveau \t Ecole ");
			ResultSet s = myInterface(request, 1);
		    int i = 0;
		    while (s.next()) {
			classes[i] = new Classe();
			classes[i].setCode(s.getInt(1));
			classes[i].setEcole(listeEcoles("select * from ecoles where code="+(s.getInt(3))+";",false)[0]);
            classes[i].setNiveau(s.getInt(2));
			classes[i].setNom(s.getString(4));
            if(printIt)
            System.out.println(classes[i].getCode()+"\t" +classes[i].getNom()+ "\t" + classes[i].getNiveau()+ "\t" +(listeEcoles("select * from ecoles where code in (select ecole from classe where code ="+classes[i].getCode()+");",false)[0].getNom()));
			i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classes;
	}
	
    
    public static Employe [] listeEmployes(String request, boolean printIt) {
		Employe e [] = new Employe [getNumberOfRows(request)];;
		try {
            if(printIt)
            System.out.println("Code\tNom\tPrenom");
		    ResultSet s = myInterface(request, 1);
			int i = 0;
		    while (s.next()) {
			e[i] = new Employe();
			e[i].setCode(s.getInt(1));
			e[i].setNom(s.getString(2));
			e[i].setPrenom(s.getString(3));
            if(printIt)
            System.out.println(e[i].getCode() +"\t"+e[i].getNom()+"\t"+e[i].getPrenom());
            i++;
			}

		} catch (Exception eh) {
			System.out.println("erreur listeEmployes()");
		}
		return e;
	}
    public static int getNumberOfRows(String request){
		int code = 0;
		try {
			ResultSet s = myInterface(request, 1);
		    while(s.next())
		    	code++;
		} catch (Exception e) {
			System.out.println("Erreur getCode()");
		}
		return code;
	}
    public static void ajouterMatiere() {
		try {
			sc.nextLine();
			Matiere M = new Matiere();
			M.setCode(getNumberOfRows("select * from matieres;") +1);
			System.out.println("\t Code: "+(M.getCode())+"\n Veuillez entrer le nom de la matiere ");
			M.setNom(sc.nextLine());
			System.out.println("La description de la matiere :");
			M.setDescription(sc.nextLine());
			myInterface("insert into matieres(nom,description) values(\""+M.getNom() +"\",\""+M.getDescription()+"\");", 2);
			System.out.println("Matiere ajoute avec succes !!!");
		} catch (Exception e) {
			// TODO: handle exception
		}

    }
    public static Matiere [] listeMatieres(String request,boolean printIt){
		Matiere [] matieres = new Matiere [getNumberOfRows(request)];
		
		try {
            if(printIt)
            System.out.println("CODE \t NOM \t DESCRIPTION ");
		    ResultSet s = myInterface(request, 1);
			int i= 0;
		    while (s.next()) {
			matieres[i] = new Matiere();
			matieres[i].setCode(s.getInt(1));
			matieres[i].setNom(s.getString(2));
			matieres[i].setDescription(s.getString(3));
            if(printIt)
            System.out.println(matieres[i].getCode()+ "\t"+ matieres[i].getNom()+ "\t" + matieres[i].getDescription());
			i++;
		}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return matieres;
	}
	
    public static void affecterMatiereEnseignant(){
		Matiere_Prof Mp = new Matiere_Prof();
		Employe employe [] = listeEmployes("select * from employes ORDER BY code;",true);
		System.out.println("Veuillez selectionner le professeur :");
		Mp.setProf(employe[(sc.nextInt() -1)]);
		Matiere matiere[] = listeMatieres("select * from matieres ORDER BY code;", true);
		System.out.println("Veuillez choisir la matiere a lui attribuer !!");
		int choix = sc.nextInt();
		while (choix != 0) {
			Mp.setMatiere(matiere[choix -1]);
			myInterface("insert into matiere_prof(prof,matiere) values("+(Mp.getProf().getCode())+","+(Mp.getMatiere().getCode())+");", 2);
            System.out.println("Attribution reussie !!");
            System.out.println("Ajouter un autre ou 0 pour arreter !!");
            listeMatieres("select * from matieres ORDER BY code;", true);
			choix = sc.nextInt();
		}
		System.out.println("Attribution reussie !!");
	}
public static boolean authentification(String login , String motDePasse){
    String pLogin, pMotDePasse;
    sc.nextLine();
    System.out.println("Merci de vous identifier dabord // Vous avez droit a 5 tentatives \n\n");
    for (int i = 4; i > 0; i--) {
        System.out.print("Login : ");
        pLogin = sc.nextLine();
        System.out.print("Password : ");
        pMotDePasse = sc.nextLine();
        if(login.equals(pLogin) && motDePasse.equals(pMotDePasse))
            return true;
        System.out.println("Identifiants errones !! \n Tentatives Restantes : "+ i);
    }
    return false;

    }
        
    public static void ajouterEleve(int codeEcole){
		Eleve eleve = new Eleve();
		do {
            eleve.setCode(getNumberOfRows("select * from eleve; ") + 1);
		System.out.println("Code : " + eleve.getCode());
		System.out.println("Veuillez renseigner son nom :");
		sc.nextLine();
		eleve.setNom(sc.nextLine());
		System.out.println("Veuillez rensigner son prenom");
		eleve.setPrenom(sc.nextLine());
		System.out.println("Veuillez renseigner son numero de telephone");
		eleve.setTel(sc.nextLine());
		Classe classe []=  listeClasse("select * from classe where ecole="+codeEcole+";",true);
		System.out.println("Veuillez choisir sa classe ");
		eleve.setClasse(listeClasse("select * from classe where code="+sc.nextInt()+";",false)[0]);
		myInterface("insert into eleve(code,nom,prenom,tel,classe) values("+(eleve.getCode())+",\""+(eleve.getNom())+"\",\""+(eleve.getPrenom())+"\",\""+(eleve.getTel())+"\","+(eleve.getClasse().getCode())+");", 2);
            System.out.println("1 pour recommencer");
        } while (sc.nextInt() == 1);
    
    }
    
    public static void ajouterEnseignant(){
		Employe E = new Employe();
		try {
		E.setCode( getNumberOfRows("select * from employes ;") +1);
		sc.nextLine();
		System.out.println("\t Son Code :" + E.getCode());
		System.out.println("Son nom: ");
		E.setNom(sc.nextLine());
		System.out.println("Son prenom : " );
		E.setPrenom(sc.nextLine());
		myInterface("insert into employes(nom,prenom) values(\""+E.getNom()+"\",\""+E.getPrenom()+"\");", 2);
		} catch (Exception e) {
			System.out.println("Erreur lors de l'ajout de L'employee");
		}
	}

    public static Connection connectDB () {
		try {
			Properties p =new Properties();
			FileInputStream file = new FileInputStream("C:/Users/DELL/Documents/Dut2/Semestre1/java/projet/Gestion_IA/src/Content/sql.properties");
			p.load(file);
			String urlPilote = p.getProperty("jdbc.driver.class");
			Class.forName(urlPilote); 
	//		System.out.println("pilote chargé");
			String urlDB=p.getProperty("jdbc.url");
			String user=p.getProperty("jdbc.login");
			String password=p.getProperty("jdbc.password");
			Connection con =DriverManager.getConnection(urlDB,user,password);
		//	System.out.println("Connexion établie");
			return con;
		}catch(Exception e) {
			System.out.println("Erreur a la connexion a la BD");
			e.printStackTrace();
			return null;
		}
	}

    public static void affecterMatiereClasse(int codeEcole){
		Classe_Matiere_Prof cmp = new Classe_Matiere_Prof();
        System.out.println("debut fonction");
		listeClasse("select * from classe where ecole="+codeEcole+";",true);
        System.out.println("after liste");
		System.out.println("Veuillez selectionner la classe !!");
		cmp.setClasse(listeClasse("select * from classe where code="+sc.nextInt()+";",false)[0]);
		int choice = 1;
		do {
			Matiere matiere [] = listeMatieres("select * from matieres;",true);
			System.out.println("Entrer le code pour ajouter une matiere : ");
			cmp.setMatiere(listeMatieres("select * from matieres where code="+sc.nextInt()+";",false)[0]);
			System.out.println("Voici la liste des enseignant dans votre ecole qui enseignent "+cmp.getMatiere().getNom());
			Employe empl [] = listeEmployes("select * from employes where code in (select employe from travailler where ecole="+codeEcole+" )and code in (select prof from matiere_prof where matiere="+cmp.getMatiere().getCode()+");",true); 
			System.out.println("Veuillez selectionner le professeur: ");
			cmp.setProf(listeEmployes("select * from employes where code="+(sc.nextInt())+";", false)[0]);
			System.out.println("Veuillez entrer le coefficient de la matiere");
			cmp.setCoefficient(sc.nextFloat());
			myInterface("insert into matiere_classe_prof(matiere,prof,classe,coef) values("+(cmp.getMatiere().getCode())+","+(cmp.getProf().getCode())+","+(cmp.getClasse().getCode())+","+(cmp.getCoefficient())+");", 2);
			System.out.println("Affectation reussie !!");
			System.out.println("0 pour recommencer ");
			choice = sc.nextInt();
		} while (choice != 0);
        System.out.println("Fin fonction");
	}

	public static int ajouterClasse(int codeEcole){
		Classe C = new Classe();
		C.setCode(getNumberOfRows("select * from classe; ") +1 );
		System.out.println("Code : "+ C.getCode());
		System.out.println("Veuillez renseigner le niveau de la classe");
		C.setNiveau(sc.nextInt());
		System.out.println("Entrer le nom de la classe");
		sc.nextLine();
		C.setNom(sc.nextLine());
		C.setEcole(listeEcoles("select * from ecoles where code="+codeEcole+";",false)[0]);
		myInterface("insert into classe(niveau,nom,ecole) values("+(C.getNiveau())+",\""+(C.getNom())+"\","+C.getEcole().getCode()+");", 2);
		System.out.println("Classe ajoute avec succes !!");
		return C.getCode();
	}

    public static ResultSet myInterface(String request,int option) {
		
		ResultSet res = null;
		try {
			switch (option) {
			case 1:
				Statement s = con.createStatement();
        		res = s.executeQuery(request); 
				break;
			case 2: 
				PreparedStatement p = con.prepareStatement(request);
				p.executeUpdate();
			default:
				break;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return res;
    }

    public static void affecterEnseignant(){
		Enseigner enseigner = new Enseigner();
		
        do {
            Employe employe [] = listeEmployes("select * from employes;",true);
		System.out.println("\t Veuillez selectionner l'enseignant :");
		enseigner.setEmploye(listeEmployes("select * from employes where code="+(sc.nextInt())+";", false)[0]);
		Ecole ecole[] =  listeEcoles("select * from ecoles;",true);
		System.out.println("Veuillez selectionner l'ecole :");
		enseigner.setEcole(listeEcoles("select * from ecoles where code="+(sc.nextInt())+";", false)[0]);
		System.out.println("Veuillez renseigner l'annee d'affectation");
		enseigner.setArrive(sc.nextInt());
		enseigner.setActif(true);
		myInterface("insert into travailler(ecole,employe,arrive,actif) values("+(enseigner.getEcole().getCode())+","+(enseigner.getEmploye().getCode())+","+(enseigner.getArrive())+","+(enseigner.getActif())+");", 2);
        System.out.println("1 pour recommencer");
        } while (sc.nextInt() == 1);
    }

    public static Ecole [] listeEcoles(String request,boolean printIt){
		Ecole ecoles [] = new Ecole [getNumberOfRows(request)];
        ResultSet s = myInterface(request, 1);
        try {
		int i= 0;
        if(printIt)
        System.out.println("CODE \t CODE DIRECTEUR \t NOM DIRECTEUR \t Nom ecole");
        while (s.next()) {
			ecoles[i] = new Ecole();
			ecoles[i].setCode(s.getInt(1));
			ecoles[i].setDirecteur(listeEmployes("select * from employes where code ="+s.getInt(2)+";",false)[0]);
			ecoles[i].setNom(s.getString(3));
            if(printIt)
            System.out.println(ecoles[i].getCode()+"\t"+ecoles[i].getDirecteur().getCode()+"\t"+ecoles[i].getDirecteur().getNom()+"\t"+ecoles[i].getNom());
			i++;
		}
       } catch (Exception e) {
            System.out.println("Erreur a l'affichage de la liste des ecoles !!");
			e.printStackTrace();
       }  
	   return ecoles;  
    }

    public static void notesManager(int codeEcole){
        System.out.println("1- Choisir une classe");
        System.out.println("2- Choisir un niveau ");
        System.out.println("3- CLassement general");   
        switch (sc.nextInt()) {
            case 1:
                System.out.println("Voici la liste des classes de votre ecole ");
                listeClasse("select * from classe where ecole="+codeEcole+";", true);
                System.out.println("\n\n\tVeuillez entrer le code de la classe choisie ");
                int codeClasse = sc.nextInt();
                System.out.println("1- Choisir un eleve de la classe ");
                System.out.println("2- Consulter les notes de tous les eleves de cette classe ");
                switch (sc.nextInt()) {
                    case 1:
                        System.out.println("Voici la liste des eleves de la classe ");
                        listeEleves("select * from eleve where classe="+codeClasse+"", true);
                        System.out.println("\n\n\tVeuillez entrer le code de l'eleve choisi ");
                        int codeEleve = sc.nextInt();
                        System.out.println("1- Choisir une matiere");
                        System.out.println("2- Afficher son bulletin ");
                        switch (sc.nextInt()) {
                            case 1:
                                System.out.println("Voici la liste de ses matieres");
                                listeMatieres("select * from matieres where code in (select matiere from matiere_classe_prof where classe="+codeClasse+");", true);
                                System.out.println("\n\n\tVeuillez entrer le code de la matiere choisie");
                                int codeMatiere = sc.nextInt();
                                Notes N =  listeNotes("select * from notes where matiere="+codeMatiere+" and eleve="+codeEleve+";")[0];
                                System.out.println("Matiere \t COEF \t NOTECC \t NOTECT \t MOYENNE \t RANG");
                                System.out.println(N.getMatiere().getNom()+"\t"+N.getCoef()+"\t"+N.getNoteCC()+"\t"+N.getNoteCT()+"\t"+N.getMoyenne()+"\t"+getRang(N, "select * from notes where matiere="+codeMatiere+" and eleve in (select code from eleve where classe="+codeClasse+");"));
                                System.out.println("1 pour voir son classement au niveau de l'ecole \n2 Pour voir voir son classement zonal ");
                                if(sc.nextInt() == 2)
                                System.out.println(N.getMatiere().getNom()+"\t"+N.getCoef()+"\t"+N.getNoteCC()+"\t"+N.getNoteCT()+"\t"+N.getMoyenne()+"\t"+getRang(N, "select * from notes where matiere="+codeMatiere+" and eleve in (select code from eleve where classe in (select code from classe where niveau in (select niveau from classe where code="+codeClasse+")));"));
                                else
                                System.out.println(N.getMatiere().getNom()+"\t"+N.getCoef()+"\t"+N.getNoteCC()+"\t"+N.getNoteCT()+"\t"+N.getMoyenne()+"\t"+getRang(N, "select * from notes where matiere="+codeMatiere+" and eleve in (select code from eleve where classe in (select code from classe where niveau in (select niveau from classe where code="+codeClasse+") and ecole="+codeEcole+"));"));
                                break;
                            case 2:
                                getBulletin(listeEleves("select * from eleve where code="+codeEleve+";", false)[0], 1, true);
                                System.out.println("1 pour voir son classement au niveau de l'ecole \n2 Pour voir voir son classement zonal ");
                                if(sc.nextInt() == 1)
                                getBulletin(listeEleves("select * from eleve where code="+codeEleve+";", false)[0], 2, true);
                                else
                                getBulletin(listeEleves("select * from eleve where code="+codeEleve+";", false)[0], 3, true);
                                break;
                            default:
                                break;
                        }
                        break;
                    case 2:
                        System.out.println("1- Choisir une matiere");
                        System.out.println("2- Afficher leur bulletins");
                        switch (sc.nextInt()) {
                            case 1:
                                System.out.println("Voici la liste de ses matieres");
                                listeMatieres("select * from matieres where code in (select matiere from matiere_classe_prof where classe="+codeClasse+");", true);
                                System.out.println("\n\n\tVeuillez entrer le code de la matiere choisie");
                                int codeMatiere = sc.nextInt();
                                getNotesElevesMatiere(listeEleves("select * from eleve where classe="+codeClasse+";", false), 1,listeMatieres("select * from matieres where code="+codeMatiere+";", false)[0],  true);
                                System.out.println("1- Voir le classement au niveau de l'ecole \n 2- Voir le classement zonal");
                                if(sc.nextInt() ==1 )
                                getNotesElevesMatiere(listeEleves("select * from eleve where classe="+codeClasse+";", false), 2,listeMatieres("select * from matieres where code="+codeMatiere+";", false)[0],  true);
                                else
                                getNotesElevesMatiere(listeEleves("select * from eleve where classe="+codeClasse+";", false), 3,listeMatieres("select * from matieres where code="+codeMatiere+";", false)[0],  true);
                                break;
                            case 2:
                                afficherBulletins(listeEleves("select * from eleve where classe="+codeClasse+";", false), 1);
                                System.out.println("1- Voir le classement au niveau de l'ecole \n 2- Voir le classement zonal");
                                if(sc.nextInt() == 1)
                                afficherBulletins(listeEleves("select * from eleve where classe="+codeClasse+";", false), 2);
                                else
                                afficherBulletins(listeEleves("select * from eleve where classe="+codeClasse+";", false), 3);
                                break;
                            default:
                                break;
                        }
                        break;
                
                    default:
                        break;
                }
            break;
        case 2:
                System.out.println("Veuillez saisir le niveau");
                int niveauClasse = sc.nextInt();
                System.out.println("1-Choisir une matiere");
                System.out.println("2- Afficher leur bulletins");
                switch (sc.nextInt()) {
                    case 1:
                        System.out.println("Voici la liste de ses matieres");
                        listeMatieres("select * from matieres ;", true);
                        System.out.println("\n\n\tVeuillez entrer le code de la matiere choisie");
                        int codeMatiere = sc.nextInt();
                        getNotesElevesMatiere(listeEleves("select * from eleve where classe in (select code from classe where niveua="+niveauClasse+");", false), 2,listeMatieres("select * from matieres where code="+codeMatiere+";", false)[0] , true);
                        System.out.println("1-Pour voir leur classement niveau zonal");
                        if(sc.nextInt() == 1)
                        getNotesElevesMatiere(listeEleves("select * from eleve where classe in (select code from classe where niveua="+niveauClasse+" and ecole="+codeEcole+");", false), 3,listeMatieres("select * from matieres where code="+codeMatiere+";", false)[0] , true);
                        break;
                    case 2:
                        afficherBulletins(listeEleves("select * from eleve where classe in (select code from classe where niveau="+niveauClasse+" and ecole="+codeEcole+");",false), 2);
                        System.out.println("1-Pour leur classement zonal ");
                        if(sc.nextInt() == 1)
                        afficherBulletins(listeEleves("select * from eleve where classe in (select code from classe where niveau="+niveauClasse+" and niveau="+niveauClasse+");",false), 3);
                        break;
                    default:
                        break;
                }

            break;
        case 3:
                System.out.println("1- Choisir une matiere");
                System.out.println("2- Afficher tous les bulletins par niveau ");
                switch (sc.nextInt()) {
                    case 1:
                        System.out.println("Voici la liste des matieres");
                        listeMatieres("select * from matieres ;", true);
                        System.out.println("\n\n\tVeuillez entrer le code de la matiere choisie");
                        int codeMatiere = sc.nextInt();
                        for (niveauClasse = 6; niveauClasse >2; niveauClasse--) {
                            System.out.println("\n\n\t\tNIVEAU : "+niveauClasse+"iem\n\n");
                            getNotesElevesMatiere(listeEleves("select * from eleve where classe in (select code from classe where niveau="+niveauClasse+" and ecole="+codeEcole+");", false), 3,listeMatieres("select * from matieres where code="+codeMatiere+";", false)[0] , true);
                        }
                        break;
                    case 2:
                    for (niveauClasse = 6; niveauClasse >2; niveauClasse--) {
                        System.out.println("\n\n\t\tNIVEAU : "+niveauClasse+"iem\n\n");
                        afficherBulletins(listeEleves("select * from eleve where classe in (select code from classe where niveau="+niveauClasse+" and ecole="+codeEcole+");",false), 3);
                    }
                    default:
                        break;
                }
                break;
        }
    }
    public static void afficherBulletins(Eleve eleves[],int niveau) {
        trierEleve(eleves);
        for (int i = 0; i < eleves.length; i++) {
            getBulletin(eleves[i], niveau, true);
        }
    }
    public static void getNotesElevesMatiere(Eleve eleves[], int niveau ,Matiere M,boolean printIt) {
        trierEleve(eleves);

        for (int i = 0; i < eleves.length; i++) {
            Notes N = listeNotes("select * from notes where matiere="+M.getCode()+" and eleve="+eleves[i].getCode()+";" )[0];
            if(printIt){
                System.out.println("\n\neleve :" + eleves[i].getPrenom() + " " + eleves[i].getNom()+"\n\n");
                System.out.println("Matiere \t COEF \t NOTECC \t NOTECT \t MOYENNE \t RANG");
                switch (niveau) {
                    case 1:
                        System.out.println(N.getMatiere().getNom()+"\t"+N.getCoef()+"\t"+N.getNoteCC()+"\t"+N.getNoteCT()+"\t"+N.getMoyenne()+"\t"+getRang(N, "select * from notes where matiere="+M.getCode()+" and eleve in (select code from eleve where classe="+eleves[i].getClasse().getCode()+");"));        
                        break;
                    case 2:
                        System.out.println(N.getMatiere().getNom()+"\t"+N.getCoef()+"\t"+N.getNoteCC()+"\t"+N.getNoteCT()+"\t"+N.getMoyenne()+"\t"+getRang(N, "select * from notes where matiere="+M.getCode()+" and eleve in (select code from eleve where classe in (select code from classe where ecole="+eleves[i].getClasse().getEcole().getCode()+" and niveau="+eleves[i].getClasse().getNiveau()+"));")); 
                        break;
                    case 3:
                        System.out.println(N.getMatiere().getNom()+"\t"+N.getCoef()+"\t"+N.getNoteCC()+"\t"+N.getNoteCT()+"\t"+N.getMoyenne()+"\t"+getRang(N, "select * from notes where matiere="+M.getCode()+" and eleve in (select code from eleve where classe in (select code from classe where niveau in (select niveau from classe where code="+eleves[i].getClasse().getCode()+")));"));
                    break;
                    default:
                        break;
                }
            }
        }
    }
    public static void getBulletin(Eleve e, int niveau, boolean printIt) {
        Eleve listeEleves[] = null ;
        if(printIt){
            System.out.println("\n\n\t\tEleve : "+ e.getPrenom() +" " +e.getNom());
            System.out.println("\n\nMatiere \t COEF \t NOTECC \t NOTECT \t MOYENNE \t RANG");
        }
        Matiere matieres [] = listeMatieres("select * from matieres where code in (select matiere from matiere_classe_prof where classe in (select classe from eleve where code="+e.getCode()+") );", false);

        for (int i = 0; i < matieres.length; i++) {
            Notes N = listeNotes("select * from notes where matiere="+matieres[i].getCode()+" and eleve="+e.getCode()+";" )[0];
           if(printIt){
            switch (niveau) {
                case 1:
                listeEleves = listeEleves("select * from eleve where classe="+e.getClasse().getCode()+";", false);
                System.out.println(N.getMatiere().getNom()+"\t"+N.getCoef()+"\t"+N.getNoteCC()+"\t"+N.getNoteCT()+"\t"+N.getMoyenne()+"\t"+getRang(N, "select * from notes where matiere="+matieres[i].getCode()+" and eleve in (select code from eleve where classe="+e.getClasse().getCode()+");"));
                    break;
                case 2:
                    listeEleves = listeEleves("select * from eleve where classe in (select code from classe where niveau in (select niveau from classe where code="+e.getClasse().getCode()+") and ecole="+e.getClasse().getNiveau()+");", false);
                    System.out.println(N.getMatiere().getNom()+"\t"+N.getCoef()+"\t"+N.getNoteCC()+"\t"+N.getNoteCT()+"\t"+N.getMoyenne()+"\t"+getRang(N, "select * from notes where matiere="+matieres[i].getCode()+" and eleve in (select code from eleve where classe in (select code from classe where ecole="+e.getClasse().getEcole().getCode()+" and niveau="+e.getClasse().getNiveau()+"));")); 
                    break;
                case 3:
                    listeEleves = listeEleves("select * from eleve where classe in (select code from classe where niveau="+e.getClasse().getNiveau()+");", false);
                    System.out.println(N.getMatiere().getNom()+"\t"+N.getCoef()+"\t"+N.getNoteCC()+"\t"+N.getNoteCT()+"\t"+N.getMoyenne()+"\t"+getRang(N, "select * from notes where matiere="+matieres[i].getCode()+" and eleve in (select code from eleve where classe in (select code from classe where niveau in (select niveau from classe where code="+e.getClasse().getCode()+")));"));
                default:
                    break;
            }
           }
        }
        if(printIt)
        System.out.println("TOTAL : "+ getMoyenneGeneral(e)[0] + "\tMoyenne General: "+ getMoyenneGeneral(e)[1] + "\t RANG: "+ getRangGeneral(e,listeEleves ));

    }

    public static int getRangGeneral(Eleve e, Eleve eleves[]) {
        int rang = 1;
        float moyennes [] = new float [eleves.length];
        for (int i = 0; i < eleves.length; i++) 
            moyennes[i] = getMoyenneGeneral(eleves[i])[1];
        for (int i = 0; i < moyennes.length; i++) 
            if(moyennes[i] > getMoyenneGeneral(e)[1])
                rang++;
        return rang;
    }


    public static float [] getMoyenneGeneral(Eleve e){
		Notes N []= listeNotes("select * from notes where eleve="+e.getCode()+";");
		float Total=0 , TotalCoef =0; 
		for (int i = 0; i < N.length; i++) {
				Total = Total + N[i].getCoef() * N[i].getMoyenne();
				TotalCoef = TotalCoef + N[i].getCoef();
		}
		float tab [] = new float[2];
		tab[0] = Total;
		tab[1] = Total/TotalCoef;
		return tab; 
	}

    public static void trierEleve(Eleve e[]) {
        boolean permu = false;
        Eleve temp ;
        do {
            permu = false;
            for (int i = 0; i < e.length -1; i++) 
                if(getMoyenneGeneral(e[i] )[1] < getMoyenneGeneral(e[i+1])[1]){
                    permu = true;
                    temp = e[i];
                    e[i] = e[i+1];
                    e[i+1] = temp;
                }
        } while (permu);
    }
}