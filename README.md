# Palfinder

# Amplify 

**Instalación**

Se tiene que instalar el CLI de Amplify, se utiliza el siguiente comando para NPM

```
npm install -g @aws-amplify/cli
```

Ingresamos a la terminal e ingresamos a donde se encuentre el proyecto.

```
cd <Raíz-Del-Proyecto>
```

Ingresar el siguiente comando para inicializar **Amplify** en nuestro proyecto.

Se ingresa el siguiente comando  

```
amplify init
```
El Amplify CLI van a solicitar el  **accessKeyId** y **secretAccessKey** para configurar los credenciales a utlizar con el amplify.

    ![AmplifyCLI] https://miro.medium.com/max/2400/1*Cm0_40ttTgSylVDCBEKLkw.png)




El CLI va a consultar si deseamos utilizar un ambiente existente

En este caso se va a utlizar el ambiente por defecto **dev**

Amplify va a utlizar por defecto los credenciales configurados previamente

Nos tiene que mostrar un mensaje de confirmación de que el ambiente ha sido configurado de manera exitosa.
