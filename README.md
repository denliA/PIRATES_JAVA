# PIRATES_java

******** Importer le projet depuis git ********  
Dans éclipse, clic droit  
Import  
Git  
Project from git (attention pas le smart import)  
Clone URL  

Entrez ce lien dans URL : https://github.com/denliA/PIRATES_java.git  
Next  
When fetching a commit also fetch its tags  
Next  

Creer un directory local, exemple : /Users/denli/git/MonDossier  (sur mac)
Initial branch : master  
Remote name : origin  
Next  
Import existing Eclipse Projects  
Cochez Search for nested projects  
Finish  

******** Lancer le projet ********  
Si run ne marche pas :  
Clic droit  
Run as  
Run configuration  

Choisir "Java Application"  
Cliquez sur Main  
Project = PIRATES  
Main class = pirates.Test  

Cliquez sur Arguments  
Program Arguments = dataEquipage.txt  
Apply  
Run  

******** Generer javadoc ********  
Dans onglet Project  
Generate javadoc  
Create Javadoc for members with visibility = Private  
Use standard doclet  
Finish  
Yes to All  

******** Remarques ********  
La javadoc est dans le dossier /doc/  
Les fichiers de sauvegarde sont dans le dossier /file/  
Si le nom de fichier entré ne se termine pas par .txt, ordi va ajouter .txt  


