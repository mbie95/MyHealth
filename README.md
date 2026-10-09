# MyHealth

To run backend, open project in IntelliJ or another compiler:
* Create locally MySql database 'myhealth', set your db username and password in .env file
* Change your #GMAIL CREDENTIALS in .env file (Google application account)
* Change your #GOOGLE OAUTH in .env file
* Change "profile.pictures.directory" property in 'application.properties' file. Frontend directory path + "/public/profile-pictures"
* Click "Run" arrow in compiler.
* To add roles to database, in your MySql client enter:
  * insert into myhealth.roles(id, name) values (1, 'ADMIN')
  * insert into myhealth.roles(id, name) values (2, 'DOCTOR')
  * insert into myhealth.roles(id, name) values (3, 'PATIENT')
* To add admin user, register user and in your MySql client enter:
  * insert into myhealth.users_roles(user_id, role_id) values (user_id, 1) #to add admin role
