package com.example.calculadoraipvg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calculadoraipvg.R;


public class MainActivity extends AppCompatActivity {
    EditText et_ev1, et_ev2, et_ev3, et_asis, et_notapresentacion, et_exam, et_notafinal;
    TextView tv_notafinal, tv_situacionFinal, tv_notapresentacion, tv_exam;
    Button btn_calcularNF, btn_calcularNP;
    double ev1, ev2, ev3, asist, np, exam, nf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_ev1 = findViewById(R.id.et_ev1);
        et_ev2 = findViewById(R.id.et_ev2);
        et_ev3 = findViewById(R.id.et_ev3);
        et_asis = findViewById(R.id.et_asis);
        et_notapresentacion = findViewById(R.id.et_notapresentacion);
        et_exam = findViewById(R.id.et_exam);
        et_notafinal = findViewById(R.id.et_notafinal);
        tv_situacionFinal = findViewById(R.id.tv_finalSituation);
        tv_notafinal = findViewById(R.id.tv_notafinal);
        tv_notapresentacion = findViewById(R.id.tv_notapresentacion);
        tv_exam = findViewById(R.id.tv_exam);
        btn_calcularNP = findViewById(R.id.btn_calcularNP);
        btn_calcularNF = findViewById(R.id.btn_calcularNF);

        //  Recuperar color original al hacer clic
        restaurarColor(et_ev1);
        restaurarColor(et_ev2);
        restaurarColor(et_ev3);
        restaurarColor(et_asis);
        restaurarColor(et_exam);
    }

    public void calcularNotaPresentacion(View view){
        try{
            // Validar que no haya campos vacios
            validarVacio("Evaluacion 1",et_ev1);
            validarVacio("Evaluacion 2",et_ev2);
            validarVacio("Evaluacion 3",et_ev3);
            validarVacio("Porcentaje de asistencia",et_asis);

            ev1 = Double.parseDouble(et_ev1.getText().toString());
            ev2 = Double.parseDouble(et_ev2.getText().toString());
            ev3 = Double.parseDouble(et_ev3.getText().toString());
            asist = Double.parseDouble(et_asis.getText().toString());

            // Valida rangos
            evaluarRangos("Evaluacion 1", ev1,1,7, et_ev1);
            evaluarRangos("Evaluacion 2", ev2,1,7, et_ev2);
            evaluarRangos("Evaluacion 3", ev3,1,7, et_ev3);
            evaluarRangos("Porcentaje Asistencia", asist,0,100,et_asis);

            np = ev1 * 0.25 + ev2 * 0.35 + ev3 * 0.4;
            btn_calcularNP.setEnabled(false);

            if(np < 2){
                et_notafinal.setText(np+"");
                tv_notafinal.setVisibility(View.VISIBLE);
                et_notafinal.setVisibility(View.VISIBLE);
                tv_situacionFinal.setText("HAS REPROBADO :C");
                tv_situacionFinal.setBackgroundColor(Color.RED);
                tv_situacionFinal.setVisibility(View.VISIBLE);
            } else if (ev1 <4 || ev2 <4 || ev3<4 || np<4 || (np<5.5 && asist<70)) {
                et_notapresentacion.setText(np+"");
                et_notapresentacion.setVisibility(View.VISIBLE);
                btn_calcularNF.setVisibility(View.VISIBLE);
                et_exam.setVisibility(View.VISIBLE);
                tv_notapresentacion.setVisibility(View.VISIBLE);
                tv_exam.setVisibility(View.VISIBLE);
            } else{
                et_notafinal.setText(np+"");
                tv_notafinal.setVisibility(View.VISIBLE);
                et_notafinal.setVisibility(View.VISIBLE);
                tv_situacionFinal.setVisibility(View.VISIBLE);
            }

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void calcularNotaFinal(View view) throws Exception {
        try {
            validarVacio("Nota examen",et_exam);
            exam = Double.parseDouble(et_exam.getText().toString());
            evaluarRangos("Nota examen", ev3,1,7, et_exam);
            nf = np * 0.6 + exam* 0.4;
            btn_calcularNF.setEnabled(false);
            if(nf < 4){
                et_notafinal.setText(nf+"");
                tv_notafinal.setVisibility(View.VISIBLE);
                et_notafinal.setVisibility(View.VISIBLE);
                tv_situacionFinal.setText("HAS REPROBADO :C");
                tv_situacionFinal.setBackgroundColor(Color.RED);
                tv_situacionFinal.setVisibility(View.VISIBLE);
            } else{
                et_notafinal.setText(nf+"");
                tv_notafinal.setVisibility(View.VISIBLE);
                et_notafinal.setVisibility(View.VISIBLE);
                tv_situacionFinal.setText("APROBASTE :)");
                tv_situacionFinal.setVisibility(View.VISIBLE);
            }

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
    public void reset(View view){
        btn_calcularNP.setEnabled(true);
        btn_calcularNF.setEnabled(true);
        et_ev1.setText("");
        et_ev2.setText("");
        et_ev3.setText("");
        et_asis.setText("");
        et_notapresentacion.setText("");
        et_exam.setText("");
        tv_situacionFinal.setBackgroundColor(Color.GREEN);
        tv_situacionFinal.setText("FELICIDADES, TE EXIMISTE!");
        tv_notapresentacion.setVisibility(View.GONE);
        et_notapresentacion.setVisibility(View.GONE);
        tv_exam.setVisibility(View.GONE);
        et_exam.setVisibility(View.GONE);
        btn_calcularNF.setVisibility(View.GONE);
        tv_notafinal.setVisibility(View.GONE);
        et_notafinal.setVisibility(View.GONE);
        tv_situacionFinal.setVisibility(View.GONE);
    }

    private void validarVacio(String nombre, EditText et) throws Exception {
        if(et.getText().toString().isEmpty()){
            et.setBackgroundColor(Color.RED);
            throw new Exception(nombre + " no puede ser estar vacio");
        }
    }

    private void evaluarRangos(String nombre, double dato, int minimo, int maximo, EditText vista) throws Exception {
        if(dato < minimo) {
            vista.setBackgroundColor(Color.RED);
            throw new Exception(nombre + " no puede ser inferior a " + minimo);
        }
        if(dato > maximo) {
            vista.setBackgroundColor(Color.RED);
            throw new Exception(nombre + " no puede ser superior a " + maximo);
        }
    }

    public void restaurarColor(EditText et){
        et.setBackgroundColor(Color.TRANSPARENT);
        et.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                et.setBackgroundColor(Color.TRANSPARENT);
            }
        });
    }
}