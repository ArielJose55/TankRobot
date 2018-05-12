#include <LEANTEC_ControlMotor.h>

ControlMotor motor(7,8,9,10,11,12);


#define MAX_LIGHT 150

int left = 0;
int right = 0;
int advance = 0;
int reverse = 0;

int cadena[4];
byte posicion = 0;
int valor;


void setup() {
  Serial.begin(9600);
 /* pinMode(LED1,OUTPUT);
  pinMode(LED2,OUTPUT);
  pinMode(LED3,OUTPUT);
  pinMode(LED4,OUTPUT);
Serial.println("");
  digitalWrite(LED1,LOW);*/
  Serial.println("Divice Conected");
}

void loop() {
  if(Serial.available()) //Nos dice si hay datos dentro del buffer
  {
    memset(cadena, 0,sizeof(cadena));//memset borra el contenido del array  "cadena" desde la posición 0 hasta el final sizeof
 
    while(Serial.available()>0) //Mientras haya datos en el buffer ejecuta la función
    {
      delay(5); //Poner un pequeño delay para mejorar la recepción de datos
      cadena[posicion]=Serial.read();//Lee un carácter del string "cadena" de la "posicion", luego lee el siguiente carácter con "posicion++"
      posicion++;
    }
    
    Serial.print(cadena[0]);
    Serial.print(cadena[1]);
    Serial.print(cadena[2]);
    Serial.print(cadena[3]);
    Serial.print("  ");
    moveLeft(cadena);
    moveRight(cadena);
    moveAdvance(cadena);
    moveReverse(cadena);
    Serial.print(left-right);
    Serial.print("  ");
    Serial.println(advance-reverse);

    motor.Motor(advance-reverse,left-right);
    posicion=0;//Ponemos la posicion a 0
  }
  delay(10);
}

void moveLeft(int data[]){
  if(data[0] == 1 && left < MAX_LIGHT){
    left += 5;
  }else if(data [0] == 0 && left > 0){
    left -= 5;
  }
}

void moveRight(int data[]){
  if(data[1] == 1 && right < MAX_LIGHT){
    right += 5;
  }else if(data [1] == 0 && right > 0){
    right -= 5;
  }
}

void moveAdvance(int data[]){
  if(data[2] == 1 && advance < MAX_LIGHT){
    advance += 5;
  }else if(data [2] == 0 && advance > 0){
    advance -= 5;
  }
}

void moveReverse(int data[]){
  if(data[3] == 1 && reverse < MAX_LIGHT){
    reverse += 5;
  }else if(data [3] == 0 && reverse > 0){
    reverse -= 5;
  }
}

