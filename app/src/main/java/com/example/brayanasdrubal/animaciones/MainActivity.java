package com.example.brayanasdrubal.animaciones;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements Animator.AnimatorListener {
    // Etiqueta que vamos a animar
    private TextView etiqueta = null;
    private float fuenteEtiqueta;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etiqueta = (TextView)this.findViewById(R.id.etiqueta);
        fuenteEtiqueta=etiqueta.getTextSize();
    }
    // Evento que se lanza cuando el usuario pulsa un botón
    public void animar(View boton) {
// Variables que se usan más abajo
        float dest = 0;
        float h, w , x , y;
        PropertyValuesHolder pvhX;
// Animación de tipo objeto
        ObjectAnimator animacion=null;
// Animación secuencial
        AnimatorSet as = null;
// Ponemos la etiqueta con su color y tamaño fuente originales
        etiqueta.setBackgroundColor(getResources().getColor(R.color.blue));
        etiqueta.setTextSize(fuenteEtiqueta);
// Si el usuario pulsa el botón...
        switch (boton.getId()) {
// Boton que hace fundido a negro o blanco
            case R.id.boton1:
// Ponemos la etiqueta en posición 0 en eje X
                etiqueta.setX(0);
// Obtenemos el botón que ha sido pulsado
                Button tButton = (Button)boton;
// Si la etiqueta no está en negro
                if (etiqueta.getAlpha() != 0)
                {
// Definimos una animación que cambia la propiedad
// alpha (opacidad) de la etiqueta al negro (0f)
                    animacion = ObjectAnimator.ofFloat(etiqueta,"alpha", 0f);
// Cambiamos la etiqueta botón
                    tButton.setText("Fundido a negro");
                }
                else
                {
// Definimos una animación que cambia la propiedad
// alpha de la etiqueta al blanco (1f)
                    animacion = ObjectAnimator.ofFloat(etiqueta,"alpha", 1f);
// Cambiamos la etiqueta botón
                    tButton.setText("Fundido a blanco");
                }
// La animación dura 5 segundos
                animacion.setDuration(5000);
                break;
// Botón Mover
            case R.id.boton2:
// Dejamos la etiqueta sin fundido
// Usamos la clase Paint para medir el tamaño que
// ocupa el texto de la etiqueta
                Paint paint = new Paint();
                float longitudEtiqueta = paint.measureText(
                        etiqueta.getText().toString());
// Vamos a mover la etiqueta hacia la izq hasta el
// final de la longitud del texto
                dest = 0 - longitudEtiqueta;
// Si la etiqueta ya está fuera de la pantalla
// volvemos a ponerla en su sitio
                if (etiqueta.getX() < 0) {
                    dest = 0;
                }
// Definimos una animación que cambia la propiedad
// X de la etiqueta hacia dest
                animacion = ObjectAnimator.ofFloat(etiqueta,"x", dest);
// Definimos la animación durante 2 segundos
                animacion.setDuration(2000);
                break;
// Animación secuencial por lotes
            case R.id.boton3:
// Ponemos la etiqueta en su sitio y sin fundido
                etiqueta.setX(0);
                etiqueta.setAlpha(1f);
// Definimos una animación que cambia el color de la
// etiqueta del azul actual al negro
                ObjectAnimator color1 =
                        ObjectAnimator.ofInt(etiqueta, "textColor",getResources().getColor(R.color.black));
// Definimos una animación que cambia de nuevo el
// color de la etiqueta del negro actual al azul
                ObjectAnimator color2 =
                        ObjectAnimator.ofInt(etiqueta,"textColor", getResources().getColor(R.color.white));
// Cambiamos el tamaño de la fuente a 40f
                ObjectAnimator fuente1 =
                        ObjectAnimator.ofFloat(etiqueta,"textSize", 40f);
// Dejamos el tamaño de la fuente inicial
                ObjectAnimator fuente2 =
                        ObjectAnimator.ofFloat(etiqueta,"textSize", fuenteEtiqueta);
// Definimos un animador por lotes
                as = new AnimatorSet();
// Ejecutamos la animación en secuencia
                as.playTogether(color1, color2, fuente1, fuente2);
// La animación dura 4 segundos
                as.setDuration(4000);
                break;
// Animación desde fichero XML
            case R.id.boton4:
// Ponemos la etiqueta en su sitio y sin fundido
                etiqueta.setX(0);
                etiqueta.setAlpha(100);
// Cargamos la animación de un archivo XML
                as = (AnimatorSet) AnimatorInflater.loadAnimator(this,R.anim.fundidos);
// Indicamos la Vista sobre la que se debe aplicar
                as.setTarget(etiqueta);
                break;
// Botón PropertiesHolder (Contenedor de propiedades)
            case R.id.boton5:
// Ponemos la etiqueta en su sitio y sin fundido
                etiqueta.setX(0);
                etiqueta.setAlpha(1f);
// Obtenemos el tamaño de la etiqueta
                h = etiqueta.getHeight();
                w = etiqueta.getWidth();
                x = etiqueta.getX();
                y = etiqueta.getY();
// Movemos la etiqueta a un lado
                etiqueta.setX(w);
                etiqueta.setY(h);
// Creamos un PropertiesHolder en el eje X e Y para
// que vuelva a la posición inicial
                pvhX = PropertyValuesHolder.ofFloat("x", x);
                PropertyValuesHolder pvhY =
                        PropertyValuesHolder.ofFloat("y", y);
// Definimos la animación con estos PropertiesHolder
                animacion = ObjectAnimator.ofPropertyValuesHolder(etiqueta, pvhX, pvhY);
// La animación dura 5 segundos
                animacion.setDuration(5000);
// Definimos un acelerador de la animación
                animacion.setInterpolator(new
                        AccelerateDecelerateInterpolator());
                break;
// Botón ViewAnimator
            case R.id.boton6:
// Ponemos la etiqueta en su sitio y sin fundido
                etiqueta.setX(0);
                etiqueta.setAlpha(1f);
// Obtenemos el tamaño de la etiqueta
                h = etiqueta.getHeight();
                w = etiqueta.getWidth();
                x = etiqueta.getX();
                y = etiqueta.getY();
// Movemos la etiqueta a un lado
                etiqueta.setX(w);
                etiqueta.setY(h);
// Definimos una animación del tipo ViewAnimator
                ViewPropertyAnimator vpa = etiqueta.animate();
// Definimos la posición final de la etiqueta en la
// animación
                vpa.x(x);
                vpa.y(y);
// La animación dura 5 segundos
                vpa.setDuration(5000);
// Definimos el listener de la animación
                vpa.setListener(this);
// Definimos un acelerador de la animación
                vpa.setInterpolator(new
                        AccelerateDecelerateInterpolator());
                break;
// Botón TypeEvaluator
            case R.id.boton7:
// Ponemos la etiqueta en su sitio y sin fundido
                etiqueta.setX(0);
                etiqueta.setAlpha(1f);
// Definimos el color inicial y final de la animación
                Integer colorIni =
                        getResources().getColor(R.color.red);
                Integer colorFin =
                        getResources().getColor(R.color.blue);
// Definimos la animación utilizando el TypeEvaluator
// ArgbEvaluator e indicando los colores inicial y
// final
                ValueAnimator colorAnimation = ValueAnimator.ofObject(
                        new ArgbEvaluator(), colorIni, colorFin);
// Definimos el listener que se lanza cada vez que es
// necesario actualizar la animación
                colorAnimation.addUpdateListener(
                        new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator
                                                                  animator) {
// Cambiamos el color de la etiqueta
                                etiqueta.setBackgroundColor((Integer)animator.getAnimatedValue());
                            }
                        });
// La animación dura 5 segundos
                colorAnimation.setDuration(5000);
// Quitamos todos los listeners de la animación
                colorAnimation.removeAllListeners();
// Añadimos el listener local
                colorAnimation.addListener(this);
// Iniciamos la animación
                colorAnimation.start();
                break;
// Botón KeyFrames (Fotogramas)
            case R.id.boton8:
// Ponemos la etiqueta en su sitio y sin fundido
                etiqueta.setX(0);
                etiqueta.setAlpha(1f);
// Obtenemos el tamaño de la etiqueta
                h = etiqueta.getHeight();
                w = etiqueta.getWidth();
                x = etiqueta.getX();
                y = etiqueta.getY();
// Fotograma de inicio : 0.2
// Valor alpha: 0.8
                Keyframe kf0 = Keyframe.ofFloat(0.2f, 0.8f);
// Fotograma intermedio: 0.5
// Valor alpha: 0.2
                Keyframe kf1 = Keyframe.ofFloat(.5f, 0.2f);
// Fotograma final: 0.8
// Valor alpha: 0.8
                Keyframe kf2 = Keyframe.ofFloat(0.8f, 0.8f);
// Definimos un PropertyHolder para almacenar los
// cambios
                PropertyValuesHolder pvhAlpha =
                PropertyValuesHolder.ofKeyframe("alpha", kf0,kf1, kf2);
// Definimos también el movimiento horizontal
                pvhX = PropertyValuesHolder.ofFloat("x", w, x);
// Definimos la animación a partir de fotogramas
                animacion =
                        ObjectAnimator.ofPropertyValuesHolder(etiqueta, pvhAlpha, pvhX);
// La animación dura 5 segundos
                animacion.setDuration(5000);
                break;
            default:
                break;
        }
        if (animacion!=null) {
// Quitamos todos los listeners de la animación
            animacion.removeAllListeners();
// Añadimos el listener local
            animacion.addListener(this);
// Iniciamos la animación
            animacion.start();
        } else
        if (as!=null) {
// Quitamos todos los listeners de la animación
            as.removeAllListeners();
// Añadimos el listener local
            as.addListener(this);
            as.start();
        }
    } // end animar
    // Métodos que lanza Android cuando ocurre un evento en la animación
    @Override
    public void onAnimationCancel(Animator animation) {
        Toast.makeText(this, "Se cancela la animación",
                Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onAnimationEnd(Animator animation) {
        Toast.makeText(this, "Finaliza la animación",
                Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onAnimationRepeat(Animator animation) {}
    @Override
    public void onAnimationStart(Animator animation) {
        Toast.makeText(this, "Empieza la animación",
                Toast.LENGTH_SHORT).show();
    }
} // end clase