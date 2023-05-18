# ABS-Fintech-Bank-NUBAN-SpringBoot
  This is the backend of an assessment project that generates a Nigerian Uniform Bank Account Number for a Fintech Business | Built in SpringBoot
  
# Frontend | Built in Angular 15

<a href="" target="_blank"></a>

# How to Use

- Clone or download source zip file
- You can run docker-compose.yaml file to kickstart the service-registry, config-server, gateway and postgres database (You can use your own local database as well)
- Update application.yaml file with your own db configurations(username and password)
- Login into postgres and create a database and table, like so:

  <b>Database</b>
     ```
        CREATE DATABSE absbank;
    ```
 <b>Table</b>
   ```  
        CREATE TABLE customer (
           id VARCHAR (32) PRIMARY KEY NOT NULL, 
           nuban VARCHAR (10) NOT NULL,
           serial_number VARCHAR (10) NOT NULL,
           bank_code VARCHAR (5) NOT NULL,
           bank TEXT NOT NULL
        );
   ```

- Install maven dependencies and run project
- To run without docker-compose.yaml, run service in the order(service-registry, config-server, gateway then absbank service)
- View all available endpoints with swagger at: http:localhost:8081/api/v1/docs/swagger-ui.html

# Contact

For inquires, support, bug reports and suggestions send me a mail@: confidostic3@gmail.com

- LinkedIn: https://www.linkedin.com/in/confidencedev/
- Twitter: @ConfidenceDev
- Instagram: @confidence.dev
