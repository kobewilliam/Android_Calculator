package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_0;//0数字按钮
    private Button btn_1;//1数字按钮
    private Button btn_2;//2数字按钮
    private Button btn_3;//3数字按钮
    private Button btn_4;//4数字按钮
    private Button btn_5;//5数字按钮
    private Button btn_6;//6数字按钮
    private Button btn_7;//7数字按钮
    private Button btn_8;//8数字按钮
    private Button btn_9;//9数字按钮
    private Button btn_point;//小数点按钮
    private Button btn_clear;//clear按钮
    private Button btn_change;//del按钮
    private Button btn_plus;//+按钮
    private Button btn_minus;//-按钮
    private Button btn_multply;//*按钮
    private Button btn_divide;//除号按钮
    private Button btn_equal;//=按钮
    private Button btn_percentage;
    private TextView showInput;
    private TextView showResult;

    private StringBuilder str = new StringBuilder();

    boolean clear_flag;//清空标识

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        btn_0 = (Button) findViewById(R.id.btn_0);
        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_3 = (Button) findViewById(R.id.btn_3);
        btn_4 = (Button) findViewById(R.id.btn_4);
        btn_5 = (Button) findViewById(R.id.btn_5);
        btn_6 = (Button) findViewById(R.id.btn_6);
        btn_7 = (Button) findViewById(R.id.btn_7);
        btn_8 = (Button) findViewById(R.id.btn_8);
        btn_9 = (Button) findViewById(R.id.btn_9);
        btn_point = (Button) findViewById(R.id.btn_point);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_change = (Button) findViewById(R.id.btn_change);
        btn_plus = (Button) findViewById(R.id.btn_plus);
        btn_minus = (Button) findViewById(R.id.btn_minus);
        btn_multply = (Button) findViewById(R.id.btn_multply);
        btn_divide = (Button) findViewById(R.id.btn_divide);
        btn_equal = (Button) findViewById(R.id.btn_equal);
        btn_percentage = (Button) findViewById(R.id.btn_percentage);
        showInput = (TextView) findViewById(R.id.id_input);
        showResult = (TextView) findViewById(R.id.id_result);

        btn_0.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_point.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
        btn_change.setOnClickListener(this);
        btn_plus.setOnClickListener(this);
        btn_minus.setOnClickListener(this);
        btn_multply.setOnClickListener(this);
        btn_divide.setOnClickListener(this);
        btn_equal.setOnClickListener(this);
        btn_percentage.setOnClickListener(this);
        showInput.setOnClickListener(this);
        showResult.setOnClickListener(this);
        clear(showInput,showResult);
        str.append("0");
    }

    @Override
    public void onClick(View view) {
        int dotPosition = str.lastIndexOf(".");
        int maxOpposition = maxOperatorPosition(str);
        int lastDotposition = dotPosition;

        try{
            switch (view.getId()){
                case R.id.btn_clear:
                    clear(showInput,showResult);
                    str.setLength(1);
                    str.replace(str.length()-1,str.length(),"0");

                    break;
                case R.id.btn_plus:
                    getOperator("+");
                    showInput.setText(str);
                    return;
                case R.id.btn_minus:
                    getOperator("-");
                    showInput.setText(str);
                    return;
                case R.id.btn_multply:
                    getOperator("*");
                    showInput.setText(str);
                    return;
                case R.id.btn_divide:
                    getOperator("/");
                    showInput.setText(str);
                    return;
                case R.id.btn_change:
                    getOperator("+/-");
                   showInput.setText(str);
                    return;

                case R.id.btn_percentage:
                    getOperator("%");
                    showInput.setText(str);
                    return;

                case R.id.btn_equal:
                    if (str.length() == 0) {str.append(0); return;}
                    if (str.length() == 1 && str.charAt(0) == '-') return;
                    if (str.length() == 2 && str.charAt(1)=='0') return;
                    DecimalFormat df = new DecimalFormat("###.###############");
                    String num = null;
                    double d = calculate(str.toString());
                    if (Double.isNaN(d) || Double.isInfinite(d)) {
                        showResult.setText("不能除以0");
                    } else {
                        try {
                            num = df.format(d);
                        } catch (Exception e) {
                            System.out.println("错误！");
                        }
                        showResult.setText("-0".equals(num) ? "0" : num);
                    }
                    showResult.setTextColor(Color.parseColor("#ff00ff"));
                    showResult.setTextSize(36);
                    return;
                case R.id.btn_point:
                    if (str.length() == 1 && str.charAt(0) == '0'){
                        str.append(".");
                    }
                    else if(maxOpposition<dotPosition&&dotPosition!=-1&&(!str.substring(lastDotposition,dotPosition).contains("+")||!str.substring(lastDotposition,dotPosition).contains("*")
                            ||!str.substring(lastDotposition,dotPosition).contains("/")||!str.substring(lastDotposition,dotPosition).contains("/")))
                    {
                        lastDotposition = dotPosition;

                    }else if(str.charAt(str.length()-1)=='*'||str.charAt(str.length()-1)=='/'||str.charAt(str.length()-1)=='+'||str.charAt(str.length()-1)=='-')
                    {
                        str.append("0.");
                    }

                    else {
                        str.append(".");
                    }
                    break;
                case R.id.btn_0:
                    if (str.length() == 1 && str.charAt(0) == '0'){
                        str.replace(str.length()-1,str.length(),"0");
                    }else {
                        str.append("0");
                    }
                    break;
                case R.id.btn_1:
                    if (str.length() == 1 && str.charAt(0) == '0'){
                        str.replace(str.length()-1,str.length(),"1");
                    }else {
                        str.append("1");
                    }
                    break;
                case R.id.btn_2:
                    if (str.length() == 1 && str.charAt(0) == '0'){
                        str.replace(str.length()-1,str.length(),"2");
                    }else {
                        str.append("2");
                    }
                    break;
                case R.id.btn_3:
                    if (str.length() == 1 && str.charAt(0) == '0'){
                        str.replace(str.length()-1,str.length(),"3");
                    }else {
                        str.append("3");
                    }
                    break;
                case R.id.btn_4:
                    if (str.length() == 1 && str.charAt(0) == '0'){
                        str.replace(str.length()-1,str.length(),"4");
                    }else {
                        str.append("4");
                    }
                    break;
                case R.id.btn_5:
                    if (str.length() == 1 && str.charAt(0) == '0'){
                        str.replace(str.length()-1,str.length(),"5");
                    }else {
                        str.append("5");
                    }
                    break;
                case R.id.btn_6:
                    if (str.length() == 1 && str.charAt(0) == '0'){
                        str.replace(str.length()-1,str.length(),"6");
                    }else {
                        str.append("6");
                    }
                    break;
                case R.id.btn_7:
                    if (str.length() == 1 && str.charAt(0) == '0'){
                        str.replace(str.length()-1,str.length(),"7");
                    }else {
                        str.append("7");
                    }
                    break;
                case R.id.btn_8:
                    if (str.length() == 1 && str.charAt(0) == '0'){
                        str.replace(0,1,"8");
                    }else {
                        str.append("8");
                    }
                    break;
                case R.id.btn_9:
                    if (str.length() == 1 && str.charAt(0) == '0'){
                        str.replace(str.length()-1,str.length(),"9");
                    }else {
                        str.append("9");
                    }
                    break;


                
            }
            showInput.setText(str);
            int len = str.length();
            if (len != 0) {
                DecimalFormat df = new DecimalFormat("###.###############");
                String num = null;
                double d = calculate(str.toString());
                if (Double.isNaN(d) || Double.isInfinite(d)) {
                    showResult.setText("Can't divided by 0");
                } else {
                    try {
                        num = df.format(d);
                    } catch (Exception e) {
                        System.out.println("Input is Wrong！");
                    }
                    showResult.setText("-0".equals(num) ? "0" : num);

                }
            }
        }catch (NumberFormatException e) {
            showResult.setText("The expression is Wrong");
            e.printStackTrace();
        }


    }

    private void getOperator(String s){
        int len = str.length();
        switch (s){
            case "-":
                if(len == 1 && str.charAt(0)=='0'){
//                    str.append("-");
                    return;
                }
                if (str.charAt(len - 1) == '*' || str.charAt(len - 1) == '/'){
                    str.append(s);
                }
                else if(judeOperator(str.charAt(len - 1) + "")){
                    str.replace(len - 1, len, s);
                }else{
                    str.append(s);
                }
                break;
            case "+/-":

                int dotPosition = str.lastIndexOf(".");
                int maxOpposition = maxOperatorPosition(str);

                if (str.toString().contains("."))
                {
                    if (maxOpposition == -1 &&len>1) //  format is 0.000xxx and no op
                    {
                        str.insert(0,"-");
                    }
                    else if (maxOpposition == 0) // format is -0.000xxx and no other op
                    {
                        str.replace(0,1,"");
                    }
                    else if (maxOpposition<dotPosition && maxOpposition != 0 && maxOpposition!=-1) // format is 0000xxxx + 0.0000xxx
                    {
                        System.out.println("hah");
                        if (str.charAt(maxOpposition)=='-' && str.charAt(maxOpposition-1)!='*'&&str.charAt(maxOpposition-1)!='/'&&str.charAt(maxOpposition-1)!='+') //format is 00xx - xx.xx
                        {
                            str.replace(maxOpposition,maxOpposition+1,"+");
                        }
                        else if (str.charAt(maxOpposition)=='-' && (str.charAt(maxOpposition-1) =='*' || str.charAt(maxOpposition-1)=='/')) //format is 00xx *- xx.xx
                    {
                        str.replace(maxOpposition,maxOpposition+1,"");
                    }else if (str.charAt(maxOpposition)=='+' ) //format is 00xx + xx.xx
                    {
                        str.replace(maxOpposition,maxOpposition+1,"-");
                    }else if (str.charAt(maxOpposition)=='*' || str.charAt(maxOpposition)=='/' ) //format is 00xx + xx.xx
                        {
                            str.insert(maxOpposition+1,"-");
                        }

                    }
                    else if(maxOpposition>dotPosition) // format is 0.000xxx + xx, the end of number contains no dot
                    {
                       if (str.charAt(len-1) == '*' || str.charAt(len-1) == '/')  // format is 0.000xxx * or /, the end of number contains no dot
                       {
                           str.append("-");

                       }
                       else if(str.charAt(len-1) == '+')    // format is 0.000xxx +, the end of number contains no dot
                       {
                           str.replace(len-1,len,"-");
                       }
                       else if (str.charAt(len-1) == '-' && str.charAt(len-2) != '*' && str.charAt(len-2) != '/')  // format is 0.000xxx -, the end of number contains no dot
                       {
                           str.replace(len-1,len,"+");
                       } else if (str.charAt(len-1) == '-' && (str.charAt(len-2) == '*' || str.charAt(len-2) == '/'))  // format is 0.000xxx *-, the end of number contains no dot
                       {
                           str.replace(len-1,len,"");
                       }

                       else if(str.charAt(maxOpposition)=='*' || str.charAt(maxOpposition)=='/'   && !judeOperator(str.substring(maxOpposition+1,maxOpposition+2)))
                       {
                           str.insert(maxOpposition+1,'-');
                       }

                       else if (str.charAt(maxOpposition)=='-' && str.charAt(maxOpposition-1)=='/' || str.charAt(maxOpposition-1)=='*'  ) // format is 0.000xxx * - 00xx, the end of number contains no dot
                       {
                           str.replace(maxOpposition,maxOpposition+1,"");
                       } else if (str.charAt(maxOpposition)=='-' && !judeOperator(str.substring(maxOpposition-1,maxOpposition))  ) // format is 0.000xxx - 00xx, the end of number contains no dot
                       {
                           str.replace(maxOpposition,maxOpposition+1,"+");
                       }else if(str.charAt(maxOpposition)=='+')// format is 0.000xxx + 00xx or 0.000xxx * + 00xx or, the end of number contains no dot
                       {
                           str.replace(maxOpposition,maxOpposition+1,"-");
                       }
                    }
                }else if(!str.toString().contains(".")) // no dot in the op
                {
                        if (maxOpposition == -1 &&str.charAt(0)!='0') // format is xx, there is no op
                        {
                            str.insert(0,"-");
                        }
                        else if(len==1 && str.charAt(0)=='0')
                        {
                            str.replace(0,1,"-");
                        }
                        else if(maxOpposition==0) // format is -xx, there is no other op
                        {
                            str.replace(0,1,"");
                        }else if(str.charAt(str.length()-1)=='*'||str.charAt(str.length()-1)=='/') // format is xx* or xx/
                        {
                        str.append("-");
                        }
                        else if(str.charAt(maxOpposition)=='-'&& (str.charAt(maxOpposition-1)=='*'||str.charAt(maxOpposition-1)=='/')) // format is xx *- xx
                        {
                        str.replace(maxOpposition,maxOpposition+1,"");
                        }
                        else if(str.charAt(maxOpposition)=='+')  // format is xx+xx
                        {
                            str.replace(maxOpposition,maxOpposition+1,"-");
                        }
                        else if(str.charAt(maxOpposition)=='-' &&(str.charAt(maxOpposition-1)!='*'||str.charAt(maxOpposition-1)!='/'||str.charAt(maxOpposition-1)!='+'))//format is xx-xx
                        {
                            str.replace(maxOpposition,maxOpposition+1,"+");
                        }
                        else if(str.charAt(maxOpposition)=='*'||str.charAt(maxOpposition)=='/')//format is xx*xx or xx/xx
                        {
                            str.insert(maxOpposition+1,"-");
                        }

                }

                if (len != 0) {
                    DecimalFormat df = new DecimalFormat("###.###############");
                    String num = null;
                    double d = calculate(str.toString());
                    if (Double.isNaN(d) || Double.isInfinite(d)) {
                        showResult.setText("Can't divided by 0");
                    } else {
                        try {
                            num = df.format(d);
                        } catch (Exception e) {
                            System.out.println("Input is Wrong！");
                        }
                        showResult.setText("-0".equals(num) ? "0" : num);

                    }
                }

                break;
            case "+":
            case "*":
            case "/":
                if (len == 0) return;
                if (judeOperator(str.charAt(len - 1)+ "")){
                    str.replace(len - 1, len, s);
                }else{

                    str.append(s);
                }
                break;
            case "%":
                int dotPosition_2 = str.lastIndexOf(".");
                int maxOpposition_2 = maxOperatorPosition(str);
//                showResult.setText("hello");
                if (str.toString().contains(".")) // the format contains dot like xx.xx
                {
//                    System.out.println(str.substring(maxOpposition_2,dotPosition_2));
//                    System.out.println(str.substring(maxOpposition_2,dotPosition_2).length());
                    if(maxOpposition_2 != -1) {
                        if (maxOpposition_2<dotPosition_2){
                        if (str.charAt(dotPosition_2 - 1) == '0' && judeOperator(str.substring(dotPosition_2 - 2, dotPosition_2 - 1))) // format is xx+0.xxx
                        {
                            str.insert(dotPosition_2 + 1, "00");
                        }
                        else if ( str.substring(maxOpposition_2 + 1, dotPosition_2).length() == 1 && str.charAt(dotPosition_2 - 1) != '0') // format is xx+x.xxx
                        {
                            str.replace(dotPosition_2, dotPosition_2 + 1, "");
                            str.insert(maxOpposition_2 + 1, "0.0");

                        }
                        else if ( str.substring(maxOpposition_2 + 1, dotPosition_2).length() == 2) // format is xx+xx.xxx
                        {
                            str.replace(dotPosition_2, dotPosition_2 + 1, "");
                            str.insert(maxOpposition_2 + 1, "0.");
                        }
                        else if ( str.substring(maxOpposition_2 + 1, dotPosition_2).length() > 2)// format is xx+xxx.xx
                        {
                            str.insert(dotPosition_2 - 2, ".");
                            str.replace(dotPosition_2 + 1, dotPosition_2 + 2, "");
                        }
                        else if(str.charAt(0)=='-' && str.substring(maxOpposition_2 , dotPosition_2).length() == 2) // format is -x.xxxx
                        {
                            str.replace(dotPosition_2, dotPosition_2 + 1, "");
                            str.insert(maxOpposition_2 + 1, "0.0");
                        }
                        }else
                            {
                                System.out.println(str.substring(maxOpposition_2+1,len-1));
                                System.out.println(str.substring(maxOpposition_2+1,len-1).length());
                                if (str.substring(maxOpposition_2+1,len).length()==1)//format is xx + xx.x + x
                                {
                                    System.out.println("jijijij");
                                    str.insert(maxOpposition_2+1,"0.0");
                                }else if(str.substring(maxOpposition_2+1,len).length()==2) // format is xx.x + xx
                                {
                                    str.insert(maxOpposition_2+1,"0.");
                                }
                                else if(str.substring(maxOpposition_2+1,len).length()>2) // format is xx.x + xx
                                {
                                    str.insert(len-2,".");
                                }
                            }
                    }
                        else  // no op
                        {

                                if (str.charAt(dotPosition_2 - 1) == '0' && str.substring(0, dotPosition_2).length() == 1) { // format is 0.xxx
                                    str.insert(dotPosition_2 + 1, "00");
                                } else if (str.substring(0, dotPosition_2).length() == 1 && str.charAt(dotPosition_2 - 1) != '0') // format is x.xxx
                                {
                                    str.replace(dotPosition_2, dotPosition_2 + 1, "");
                                    str.insert(0, "0.0");

                                } else if (str.substring(0, dotPosition_2).length() == 2) // format is xx.xxx
                                {
                                    str.replace(dotPosition_2, dotPosition_2 + 1, "");
                                    str.insert(0, "0.");
                                } else if (str.substring(0, dotPosition_2).length() > 2) {
                                    str.insert(dotPosition_2 - 2, ".");
                                    str.replace(dotPosition_2 + 1, dotPosition_2 + 2, "");
                                }


                        }
                }else // format is xx
                    {
                        if (str.charAt(0)=='0' && len == 1) // format is 0
                        {
                            str.replace(0,1,"0");
                        }
                        else if(len==1 && str.charAt(0)!='0') // format is x
                        {
                            str.insert(0,"0.0");
                        }
                        else if(len==2 && str.charAt(0)!='-') // format is xx
                        {
                            str.insert(0,"0.");
                        }else if(len==2 && str.charAt(0)=='-')
                        {
                            str.insert(1,"0.0");
                        }
                        else  // there are  ops
                        {

                            if(!judeOperator(str.substring(len-3,len-2)) && !judeOperator(str.substring(len-2,len-1)))

                            {
                                System.out.println(str.substring(len-2,len-1));
                                str.insert(len-2,".");
                            }
                            else if(judeOperator(str.substring(len-3,len-2)))
                            {
                                str.insert(len-2,"0.");
                            }else if(judeOperator(str.substring(len-2,len-1)))
                            {
                                str.insert(len-1,"0.0");
                            }


                        }
                    }
                if (len != 0) {
                    DecimalFormat df = new DecimalFormat("###.###############");
                    String num = null;
                    double d = calculate(str.toString());
                    if (Double.isNaN(d) || Double.isInfinite(d)) {
                        showResult.setText("Can't divided by 0");
                    } else {
                        try {
                            num = df.format(d);
                        } catch (Exception e) {
                            System.out.println("Input is Wrong！");
                        }
                        showResult.setText("-0".equals(num) ? "0" : num);

                    }
                }
                break;
        }
    }

    private int maxOperatorPosition(StringBuilder s) {

        int plusPosition = s.lastIndexOf("+");
        int minusPosition = s.lastIndexOf("-");
        int multiplyPosition = s.lastIndexOf("*");
        int dividedPosition = s.lastIndexOf("/");
        int max1 = Math.max(plusPosition, minusPosition);
        int max2 = Math.max(multiplyPosition, dividedPosition);

        return Math.max(max1, max2);

    }




    private boolean judeOperator(String s){
        return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/");
    }


    private void clear(TextView showInput, TextView showResult){
        showInput.setText("0");
        showResult.setText("0");
        showInput.setTextColor(Color.parseColor("#aaaaaa"));
        showResult.setTextColor(Color.parseColor("#aaaaaa"));
        showResult.setTextSize(28);

    }
    private double calculate(String s) {
        Queue<String> q = getPostfixExpression(s); // 中缀表达式转为后缀表达式
        return calculatePostfixExpression(q);
    }

    Stack<Double> stack = new Stack<>();

    private double calculatePostfixExpression(Queue<String> queue) {
        stack.clear();
        int len = queue.size();
        double num1 = 0.0, num2 = 0.0, num3 = 0.0;
        for (int i = 0; i < len; ++i) {
            String s = queue.poll();
            if (!judeOperator(s)) {
                stack.push(Double.valueOf(s));
            } else {
                if (stack.isEmpty()) return 0.0;
                num2 = stack.pop();
                if (stack.isEmpty()) return num2;
                num1 = stack.pop();
                switch (s) {
                    case "+":
                        num3 = num1 + num2;
                        break;
                    case "-":
                        num3 = num1 - num2;
                        break;
                    case "*":
                        num3 = num1 * num2;
                        break;
                    case "/":
                        num3 = num1 / num2;
                        break;
                }
                stack.push(num3);
            }
        }
        return stack.peek();
    }

    Stack<Character> stack2 = new Stack<>();
    Queue<String> queue2 = new LinkedList<>();
    StringBuilder strNum = new StringBuilder();

    // 获得后缀表达式
    public Queue<String> getPostfixExpression(String s) {
        stack2.clear();
        int len = s.length();
        strNum.setLength(0);
        queue2.clear();
        char temp = ' ';
        for (int i = 0; i < len; ++i) {
            temp = str.charAt(i);
            if (temp >= '0' && temp <= '9' || temp == '.') {
                strNum.append(temp);
            } else {
                if (i == 0 || judeOperator(str.charAt(i - 1) + "")) {
                    // 考虑负数的情况，比如乘以除以负数
                    strNum.append(temp);
                    continue;
                }
                queue2.add(strNum.toString()); // 数字进队列
                strNum.setLength(0);
                if (stack2.isEmpty()) {
                    stack2.push(temp);
                } else {
                    while (!stack2.isEmpty()) {
                        char top = stack2.peek();
                        if (getPriority(top) >= getPriority(temp)) {
                            queue2.add(top + "");
                            stack2.pop();
                        } else {
                            break;
                        }
                    }
                    stack2.push(temp);
                }
            }
        }
        if (strNum.length() != 0) {
            queue2.add(strNum.toString()); // 数字进队列
        }
        if (stack2.isEmpty()) {
            stack2.push(temp);
        } else {
            while (!stack2.isEmpty()) {
                char top = stack2.peek();
                queue2.add(top + "");
                stack2.pop();
            }
        }
        return queue2;
    }

    private int getPriority(char top) {
        if (top == '+' || top == '-')
            return 1;
        return 2; // 只有加减乘除
    }


}
