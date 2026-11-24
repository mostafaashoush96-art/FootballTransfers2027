package com.footballtransfers.game;

import android.app.*;
import android.os.Bundle;
import android.content.*;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.*;
import android.widget.*;
import java.util.*;

public class MainActivity extends Activity {
    LinearLayout root, body;
    TextView budgetText, clubText, notice;
    SharedPreferences prefs;
    String club="مانشستر سيتي", leagueFilter="كل الدوريات";
    int budget=200, season=2027, played=0, wins=0, draws=0, losses=0;
    ArrayList<Player> squad=new ArrayList<>();
    ArrayList<Player> market=new ArrayList<>();
    ArrayList<String> transferLog=new ArrayList<>();
    final String[] leagues={"كل الدوريات","إنجلترا","إسبانيا","إيطاليا","ألمانيا","فرنسا","مصر","السعودية","هولندا","البرتغال","تركيا"};
    final String[] clubs={"مانشستر سيتي","ليفربول","أرسنال","مانشستر يونايتد","تشيلسي","ريال مدريد","برشلونة","أتلتيكو مدريد","بايرن ميونخ","بوروسيا دورتموند","إنتر ميلان","ميلان","يوفنتوس","باريس سان جيرمان","مارسيليا","الأهلي المصري","الزمالك","بيراميدز","الهلال","النصر","الاتحاد","أياكس","بنفيكا","غلطة سراي"};
    static class Player {
        String name,pos,league,club;
        int value,age;
        Player(String n,String p,String l,String c,int v,int a){name=n;pos=p;league=l;club=c;value=v;age=a;}
    }
    @Override public void onCreate(Bundle b){
        super.onCreate(b);
        prefs=getSharedPreferences("career_v2",MODE_PRIVATE);
        loadDatabase();
        club=prefs.getString("club",club); budget=prefs.getInt("budget",200);
        season=prefs.getInt("season",2027); played=prefs.getInt("played",0);
        wins=prefs.getInt("wins",0); draws=prefs.getInt("draws",0); losses=prefs.getInt("losses",0);
        loadSquad();
        shell(); marketScreen();
    }
    void loadDatabase(){
        add("كيليان مبابي","مهاجم","إسبانيا","ريال مدريد",180,27);
        add("فينيسيوس جونيور","جناح","إسبانيا","ريال مدريد",160,26);
        add("جود بيلينغهام","وسط","إسبانيا","ريال مدريد",150,23);
        add("لامين يامال","جناح","إسبانيا","برشلونة",140,19);
        add("رافينيا","جناح","إسبانيا","برشلونة",85,29);
        add("روبرت ليفاندوفسكي","مهاجم","إسبانيا","برشلونة",25,38);
        add("إيرلينغ هالاند","مهاجم","إنجلترا","مانشستر سيتي",170,26);
        add("فيل فودين","وسط","إنجلترا","مانشستر سيتي",110,26);
        add("بوكايو ساكا","جناح","إنجلترا","أرسنال",120,25);
        add("ديكلان رايس","وسط","إنجلترا","أرسنال",100,27);
        add("محمد صلاح","جناح","إنجلترا","ليفربول",90,34);
        add("فيرجيل فان دايك","مدافع","إنجلترا","ليفربول",22,35);
        add("كول بالمر","وسط","إنجلترا","تشيلسي",115,24);
        add("برونو فيرنانديز","وسط","إنجلترا","مانشستر يونايتد",55,32);
        add("عمر مرموش","مهاجم","إنجلترا","مانشستر سيتي",65,27);
        add("هاري كين","مهاجم","ألمانيا","بايرن ميونخ",100,33);
        add("جمال موسيالا","وسط","ألمانيا","بايرن ميونخ",130,23);
        add("فلوريان فيرتز","وسط","ألمانيا","ليفربول",130,23);
        add("عثمان ديمبيلي","جناح","فرنسا","باريس سان جيرمان",90,29);
        add("فيتينيا","وسط","فرنسا","باريس سان جيرمان",85,26);
        add("لاوتارو مارتينيز","مهاجم","إيطاليا","إنتر ميلان",95,29);
        add("نيكولو باريلا","وسط","إيطاليا","إنتر ميلان",75,29);
        add("رافائيل لياو","جناح","إيطاليا","ميلان",85,27);
        add("كينان يلدز","جناح","إيطاليا","يوفنتوس",65,21);
        add("محمد الشناوي","حارس","مصر","الأهلي المصري",3,37);
        add("إمام عاشور","وسط","مصر","الأهلي المصري",12,28);
        add("أحمد سيد زيزو","جناح","مصر","الأهلي المصري",10,30);
        add("حسين الشحات","جناح","مصر","الأهلي المصري",4,34);
        add("أحمد فتوح","ظهير","مصر","الزمالك",4,28);
        add("مصطفى شوبير","حارس","مصر","الأهلي المصري",2,26);
        add("سالم الدوسري","جناح","السعودية","الهلال",18,35);
        add("روبن نيفيز","وسط","السعودية","الهلال",28,29);
        add("كريستيانو رونالدو","مهاجم","السعودية","النصر",12,41);
        add("ساديو ماني","جناح","السعودية","النصر",10,34);
        add("رياض محرز","جناح","السعودية","الأهلي السعودي",18,35);
        add("كريم بنزيما","مهاجم","السعودية","الاتحاد",8,39);
        add("فيكتور أوسيمين","مهاجم","تركيا","غلطة سراي",85,27);
        add("حكيم زياش","جناح","تركيا","غلطة سراي",8,33);
        add("أنطوان غريزمان","مهاجم","إسبانيا","أتلتيكو مدريد",25,35);
        add("فيديريكو فالفيردي","وسط","إسبانيا","ريال مدريد",120,28);
        add("أشرف حكيمي","ظهير","فرنسا","باريس سان جيرمان",75,27);
    }
    void add(String n,String p,String l,String c,int v,int a){market.add(new Player(n,p,l,c,v,a));}
    void shell(){
        root=new LinearLayout(this); root.setOrientation(1); root.setBackgroundColor(Color.rgb(242,246,243));
        LinearLayout head=new LinearLayout(this); head.setOrientation(1); head.setPadding(18,20,18,16); head.setBackgroundColor(Color.rgb(16,43,32));
        head.addView(txt("FOOTBALL TRANSFERS 2027",21,true,Color.WHITE));
        head.addView(txt("مدير ناديك يبدأ من هنا ⚽",15,true,Color.rgb(190,235,205)));
        root.addView(head);
        LinearLayout summary=new LinearLayout(this); summary.setOrientation(1); summary.setPadding(16,10,16,8);
        clubText=txt("",17,true,Color.rgb(20,55,35)); summary.addView(clubText);
        budgetText=txt("",15,true,Color.rgb(20,130,75)); summary.addView(budgetText); root.addView(summary);
        LinearLayout nav=new LinearLayout(this); nav.setPadding(8,4,8,8);
        addNav(nav,"الانتقالات",v->marketScreen()); addNav(nav,"فريقي",v->squadScreen());
        addNav(nav,"المباريات",v->matchScreen()); addNav(nav,"النادي",v->clubScreen());
        root.addView(nav);
        ScrollView sc=new ScrollView(this); body=new LinearLayout(this); body.setOrientation(1); body.setPadding(14,4,14,18);
        sc.addView(body); root.addView(sc,new LinearLayout.LayoutParams(-1,0,1));
        notice=txt("ابدأ مسيرتك!",13,false,Color.DKGRAY); notice.setPadding(14,10,14,10); root.addView(notice);
        setContentView(root); update();
    }
    interface Click {void run(View v);}
    void addNav(LinearLayout n,String label,Click c){Button b=btn(label); b.setTextSize(11); b.setOnClickListener(v->c.run(v)); n.addView(b,new LinearLayout.LayoutParams(0,-2,1));}
    void update(){clubText.setText("🏟 ناديك: "+club+"  |  موسم "+season); budgetText.setText("الميزانية: "+budget+" مليون €   •   "+squad.size()+" صفقة"); save();}
    void marketScreen(){
        body.removeAllViews(); body.addView(txt("🌍 سوق الانتقالات",22,true,Color.rgb(16,65,40)));
        body.addView(txt("قاعدة بيانات تجريبية قابلة للتوسيع. القيم تقديرية وليست أسعاراً رسمية.",12,false,Color.GRAY));
        Spinner sp=new Spinner(this); ArrayAdapter<String> ad=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,leagues);
        sp.setAdapter(ad); sp.setSelection(Arrays.asList(leagues).indexOf(leagueFilter)); body.addView(sp);
        LinearLayout searchRow=new LinearLayout(this); EditText search=new EditText(this); search.setSingleLine(true); search.setHint("ابحث عن لاعب...");
        searchRow.addView(search,new LinearLayout.LayoutParams(0,-2,1)); body.addView(searchRow);
        LinearLayout results=new LinearLayout(this); results.setOrientation(1); body.addView(results);
        Runnable render=()->{
            results.removeAllViews(); String q=search.getText().toString().trim().toLowerCase(Locale.ROOT);
            for(Player p:market){
                if(!leagueFilter.equals("كل الدوريات")&&!p.league.equals(leagueFilter))continue;
                if(!p.name.toLowerCase(Locale.ROOT).contains(q))continue;
                LinearLayout card=card(); LinearLayout row=new LinearLayout(this); row.setGravity(Gravity.CENTER_VERTICAL);
                LinearLayout info=new LinearLayout(this); info.setOrientation(1);
                info.addView(txt(p.name,16,true,Color.rgb(25,45,35)));
                info.addView(txt(p.pos+" • "+p.league+" • "+p.club,12,false,Color.DKGRAY));
                info.addView(txt("العمر "+p.age+" • القيمة "+p.value+" مليون €",12,false,Color.GRAY));
                row.addView(info,new LinearLayout.LayoutParams(0,-2,1));
                Button buy=btn(owned(p.name)?"في فريقك":"تفاوض");
                buy.setEnabled(!owned(p.name)); buy.setOnClickListener(v->negotiate(p));
                row.addView(buy); card.addView(row); results.addView(card);
            }
            if(results.getChildCount()==0)results.addView(txt("لا توجد نتائج مطابقة.",14,false,Color.GRAY));
        };
        sp.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener(){
            public void onNothingSelected(android.widget.AdapterView<?> a){}
            public void onItemSelected(android.widget.AdapterView<?> a,View v,int pos,long id){leagueFilter=leagues[pos];render.run();}
        });
        search.addTextChangedListener(new android.text.TextWatcher(){public void beforeTextChanged(CharSequence s,int st,int c,int a){} public void onTextChanged(CharSequence s,int st,int before,int count){render.run();} public void afterTextChanged(android.text.Editable e){}});
        render.run();
    }
    void negotiate(Player p){
        EditText offer=new EditText(this); offer.setInputType(2); offer.setHint("قيمة العرض بالمليون € (قيمة اللاعب "+p.value+")");
        offer.setText(String.valueOf(p.value));
        new AlertDialog.Builder(this).setTitle("مفاوضات مع "+p.name).setMessage("النادي الحالي: "+p.club+"\nقدّم عرضك لإتمام الصفقة.")
        .setView(offer).setNegativeButton("إلغاء",null).setPositiveButton("إرسال العرض",(d,w)->{
            int amount; try{amount=Integer.parseInt(offer.getText().toString());}catch(Exception e){amount=0;}
            if(amount<=0){notice.setText("اكتب قيمة عرض صحيحة.");return;}
            if(amount>budget){notice.setText("ميزانيتك لا تكفي لهذا العرض.");return;}
            int threshold=(int)(p.value*0.85);
            if(amount>=threshold){
                budget-=amount; Player signed=new Player(p.name,p.pos,p.league,club,amount,p.age); squad.add(signed);
                transferLog.add("ضم "+p.name+" مقابل "+amount+" مليون €");
                notice.setText("تم قبول العرض! "+p.name+" أصبح لاعباً في "+club+" 🎉");
            }else notice.setText("النادي رفض العرض. جرّب عرضاً أعلى من "+threshold+" مليون €.");
            update(); marketScreen();
        }).show();
    }
    void squadScreen(){
        body.removeAllViews(); body.addView(txt("👕 قائمة الفريق",22,true,Color.rgb(16,65,40)));
        if(squad.isEmpty())body.addView(txt("لم تتعاقد مع لاعبين بعد. اذهب إلى سوق الانتقالات.",14,false,Color.GRAY));
        for(Player p:new ArrayList<>(squad)){
            LinearLayout c=card(); LinearLayout r=new LinearLayout(this); r.setGravity(Gravity.CENTER_VERTICAL);
            LinearLayout info=new LinearLayout(this); info.setOrientation(1);
            info.addView(txt(p.name,16,true,Color.BLACK)); info.addView(txt(p.pos+" • قيمة الشراء "+p.value+" مليون €",12,false,Color.GRAY));
            r.addView(info,new LinearLayout.LayoutParams(0,-2,1)); Button sell=btn("بيع اللاعب");
            sell.setOnClickListener(v->{new AlertDialog.Builder(this).setTitle("بيع "+p.name).setMessage("تأكيد البيع مقابل "+p.value+" مليون €؟")
                .setNegativeButton("إلغاء",null).setPositiveButton("بيع",(d,w)->{squad.remove(p);budget+=p.value;transferLog.add("بيع "+p.name+" مقابل "+p.value+" مليون €");notice.setText("تمت عملية البيع.");update();squadScreen();}).show();});
            r.addView(sell); c.addView(r); body.addView(c);
        }
        body.addView(txt("📋 سجل الانتقالات",18,true,Color.rgb(16,65,40)));
        for(String s:transferLog)body.addView(txt("• "+s,13,false,Color.DKGRAY));
    }
    void matchScreen(){
        body.removeAllViews(); body.addView(txt("🏆 مركز المباريات",22,true,Color.rgb(16,65,40)));
        body.addView(txt("لعبت: "+played+"   فوز: "+wins+"   تعادل: "+draws+"   خسارة: "+losses,15,true,Color.DKGRAY));
        body.addView(txt("محاكاة نتائج مبسطة للتجربة. لا يوجد محرك مباريات مرئي بعد.",12,false,Color.GRAY));
        Button play=btn("العب المباراة التالية");
        play.setOnClickListener(v->{
            Random r=new Random(); int our=r.nextInt(5), opp=r.nextInt(5); played++;
            String result="تعادل"; if(our>opp){wins++;result="فوز";}else if(our<opp){losses++;result="خسارة";}else draws++;
            notice.setText("نتيجة "+club+": "+our+" - "+opp+" ("+result+")");
            update(); matchScreen();
        }); body.addView(play);
        Button next=btn("بدء موسم جديد"); next.setOnClickListener(v->{season++;played=0;wins=0;draws=0;losses=0;notice.setText("بدأ موسم "+season);update();matchScreen();}); body.addView(next);
    }
    void clubScreen(){
        body.removeAllViews(); body.addView(txt("🏟 إدارة النادي",22,true,Color.rgb(16,65,40)));
        body.addView(txt("اختار النادي الذي تريد إدارته. تغيير النادي يبدأ مسيرة جديدة.",13,false,Color.GRAY));
        for(String c:clubs){Button b=btn(c); b.setOnClickListener(v->new AlertDialog.Builder(this).setTitle("إدارة "+c+"؟").setMessage("سيتم بدء مسيرة جديدة بميزانية 200 مليون يورو.").setNegativeButton("رجوع",null).setPositiveButton("اختيار",(d,w)->{club=c;budget=200;squad.clear();played=wins=draws=losses=0;transferLog.clear();notice.setText("بدأت مسيرتك مع "+club);update();marketScreen();}).show());body.addView(b);}
    }
    boolean owned(String n){for(Player p:squad)if(p.name.equals(n))return true;return false;}
    void loadSquad(){String raw=prefs.getString("squad","");if(raw.isEmpty())return;for(String name:raw.split("\\\\|"))for(Player p:market)if(p.name.equals(name)){squad.add(new Player(p.name,p.pos,p.league,club,p.value,p.age));break;}}
    void save(){if(prefs==null)return;StringBuilder b=new StringBuilder();for(Player p:squad){if(b.length()>0)b.append("|");b.append(p.name);}
        prefs.edit().putString("club",club).putInt("budget",budget).putInt("season",season).putInt("played",played).putInt("wins",wins).putInt("draws",draws).putInt("losses",losses).putString("squad",b.toString()).apply();}
    LinearLayout card(){LinearLayout c=new LinearLayout(this);c.setOrientation(1);c.setPadding(12,10,12,10);GradientDrawable bg=new GradientDrawable();bg.setColor(Color.WHITE);bg.setCornerRadius(16);bg.setStroke(1,Color.rgb(220,230,223));c.setBackground(bg);LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2);lp.bottomMargin=9;c.setLayoutParams(lp);return c;}
    TextView txt(String s,int size,boolean bold,int color){TextView t=new TextView(this);t.setText(s);t.setTextSize(size);t.setTextColor(color);t.setGravity(Gravity.RIGHT);t.setTextDirection(View.TEXT_DIRECTION_RTL);t.setPadding(4,5,4,5);if(bold)t.setTypeface(Typeface.DEFAULT,Typeface.BOLD);return t;}
    Button btn(String s){Button b=new Button(this);b.setText(s);b.setTextSize(11);b.setAllCaps(false);b.setTextColor(Color.WHITE);b.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.rgb(24,125,75)));return b;}
}
