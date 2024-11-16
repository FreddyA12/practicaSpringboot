# Ejercicio SpringBoot


- **CustomerController**: Gestiona las operaciones relacionadas con los clientes.
- **AddressController**: Gestiona las operaciones relacionadas con las direcciones de los clientes.


## CustomerController

### Funcionalidades

#### **Funcionalidad para buscar y obtener un listado de clientes**
- **Ruta:** `GET /customer`
- **Parametros:** identificationNumber o name


#### **Funcionalidad para crear un nuevo cliente con la dirección matriz**
- **Ruta:** `POST /customer`
- **Body (JSON):**
  ```json
  {
    "identificationType": "RUC",
    "identificationNumber": "1805302760",
    "names": "Nadie Nadie",
    "email": "freddy@sd.com",
    "phoneNumber": "0991391460",
    "mainProvince": "Tungurahuaa",
    "mainCity": "Ambato",
    "mainAddress": " Calle1"
  }

#### **Funcionalidad para editar los datos del cliente**
- **Ruta:** `PUT /customer`
- **Body (JSON):**
- Solo los valores que se quieren editar*
  ```json, 
  {
    "id" : "1",
    "identificationType": "RUC",
    "identificationNumber": "1805302760",
    "names": "Nadie Nadie",
    "email": "freddy@sd.com"
  }

#### **Funcionalidad para eliminar un cliente**
- **Ruta:** `DELETE /customers/{customerId}`

## AddressController
#### **Funcionalidad para registrar una nueva dirección por cliente**
- **Ruta:** `POST /address`
- **Body (JSON):**
  ```json
  {
     "province": "Pastaza",
    "city": "Puyo",
    "address": "Calle 1",
    "customerId": 1
  }

#### **Funcionalidad para listar las direcciones adicionales del cliente**
- **Ruta:** `GET /address/{customerId}`


