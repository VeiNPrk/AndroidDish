package com.example.vnprk.android1final.model;

import android.content.Context;
import android.widget.Toast;

import com.example.vnprk.android1final.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by VNPrk on 01.12.2016.
 */
public class DataClass {

    Context context;

    public DataClass(Context _context){
        context=_context;
    }

    public boolean serializeObject(Object obj, String fileName) {
        File file;
        try {
            file = new File(context.getFilesDir() + File.separator + fileName);
        } catch (Exception exc) {
            exc.printStackTrace();
            return false;
        }
        FileOutputStream fileOut;
        ObjectOutputStream out;
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        try {
            fileOut = new FileOutputStream(file, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        try {
            out = new ObjectOutputStream(fileOut);
            out.writeObject(obj);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Object deserealizeObject(String fileName) {
        FileInputStream fileIn = null;
        ObjectInputStream in = null;
        Object result;
        try {
            fileIn = new FileInputStream(context.getFilesDir() + File.separator + fileName);
            in = new ObjectInputStream(fileIn);
            result = in.readObject();
            in.close();
            fileIn.close();
        } catch (FileNotFoundException e) {
            //Toast.makeText(context, context.getString(R.string.exception_file_not_found), Toast.LENGTH_SHORT).show();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            //Toast.makeText(context, context.getString(R.string.exception_io), Toast.LENGTH_SHORT).show();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            //Toast.makeText(context, context.getString(R.string.exception_class_not_found), Toast.LENGTH_SHORT).show();
            return null;
        } catch (ClassCastException e) {
            //Toast.makeText(context, context.getString(R.string.exception_class_cast), Toast.LENGTH_SHORT).show();
            return null;
        } finally {
            try {
                if(in != null) {in.close();}
                if (fileIn != null) {fileIn.close();}
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public boolean deleteFile(String fileName) {
        File file;
        boolean tf=false;
        try {
            file = new File(context.getFilesDir() + File.separator + fileName);
        } catch (Exception exc) {
            exc.printStackTrace();
            return false;
        }
        if (file.exists()) {
            try {
                file.delete();
                tf = true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return tf;
    }
    public void initStartFiles() {
        File fileDish, fileKategoria;
        String fileDishes = context.getString(R.string.file_dishes);
        String fileKategories = context.getString(R.string.file_kategories);
        try {
            fileDish = new File(context.getFilesDir() + File.separator + fileDishes);
            fileKategoria = new File(context.getFilesDir() + File.separator + fileKategories);
        } catch (Exception exc) {
            exc.printStackTrace();
            return;
        }
        if(!fileDish.exists()) {
            serializeObject(initDishes(), fileDishes);
        }
        if(!fileKategoria.exists()) {
            serializeObject(initKategories(), fileKategories);
        }
    }

    private ArrayList<Dish> initDishes() {
        ArrayList<Dish> dishes = new ArrayList<Dish>();
        dishes.add(new Dish("Сальваторе", "Листья салата с копчёным цыплёнком, ароматным манго, свежими томатами и персиковым соусом с добавлением майонеза.", 1, 279, 225, "salatsalvatore"));
        dishes.add(new Dish("С сёмгой", "Ломтики слабосолёной сёмги, свежий огурец, томаты черри, хрустящие листья салатов Айсберг и Руккола, сельдерей, болгарский перец и красный лук, с яйцом пашот и лимонной заправкой. ", 1, 349, 260, "salatsemga"));
        dishes.add(new Dish("С мидиями", "Изысканное сочетание мидий, слабосолёного лосося, перепелиных яиц, свежего огурца, маслин и листьев салата Айсберг, Ромен и Руккола с сыром Грана Падано, сельдереем, болгарским перцем, заправкой Итальяно и лимоном.", 1, 389, 365, "salat_s_midiyami"));
        dishes.add(new Dish("Греческий", "Классический салат из свежих огурцов и томатов, сладкого болгарского перца, нежнейшего сыра Фета, красного лука и маслин под соусом ЭльГреко.", 1, 289, 220, "salat_grecheskiy"));
        dishes.add(new Dish("Цезарь с тигровыми креветками", "Популярный салат из зелёных листьев салата Айсберг и сыра Пармезан, с тигровыми креветками, под соусом Цезарь с хрустящими крутонами, маслинами и томатами черри. ", 1, 389, 270, "salat_tsezar_s_krevetkoy"));
        dishes.add(new Dish("Цезарь с курицей", "Популярный салат из зелёных листьев салата Айсберг и сыра Пармезан, с куриным филе, под соусом Цезарь с хрустящими крутонами, маслинами и томатами черри.", 1, 299, 270, "salat_tsezar_s_tsyiplenkom"));

        dishes.add(new Dish("Фокачча", "Итальянские хлебные лепешки", 2, 119, 130, "fokachcha"));
        dishes.add(new Dish("Антипасти", "Ароматный цыпленок и утиное филе горячего копчения, запеченная в травах шейка с томатами черри, оливками и маслинами.", 2, 479, 160, "antipasti_f331"));
        dishes.add(new Dish("Капрезе", "Классика Итальянской кухни. Свежие томаты, нежный сыр Моцарелла, зелень салата Латук, Ромен и базилик с cоусом Песто.", 2, 349, 230, "kapreze"));
        dishes.add(new Dish("Ассорти сыров", "Ассорти из пяти сыров: Пармезан «Джугос» , Грюйер, Маасдам, «Рокфорти», Гауда с орешками. Подается с ароматным медом и виноградом.", 2, 419, 220, "assorti_syirov"));

        dishes.add(new Dish("Сырный", "Популярный крем-суп на основе нежнейшего сыра и картофеля, с кусочками куриного филе и чесночными крутонами.", 3, 229, 300, "sup_syirnyiy"));
        dishes.add(new Dish("Бульёон с говядиной", "Традиционный бульон с мясом, зеленью, сыром пармезан и крутонами - идеальный баланс сытного и легкого блюда.", 3, 259, 310, "sup_s_govyadinoy"));
        dishes.add(new Dish("Бьянко", "Лёгкий суп с белыми грибами, запечённым картофелем, морковью и луком.", 3, 179, 300, "sup_byanko"));
        dishes.add(new Dish("Портобелло", "Грибной крем-суп. Подаётся с крутонами. ", 3, 229, 300, "sup_portobello"));
        dishes.add(new Dish("Минестроне", "Ароматный овощной суп с говядиной, запечённой с итальянскими травами, бальзамическим уксусом и свежим базиликом.", 3, 219, 300, "sup_ministrone"));

        dishes.add(new Dish("Лазанья куриная", "Итальянская лазанья с рубленным филе цыплёнка и сыром Моцарелла в томатном соусе.", 4, 309, 360, "lazanyakurinaya"));
        dishes.add(new Dish("Лазанья мясная", "Классическая мясная лазанья с сыром Моцарелла в сочетании с томатным соусом.", 4, 309, 360, "lazanyamyasnaya"));
        dishes.add(new Dish("Равиоли иль грачио", "Нежные равиоли с начинкой из креветок и краба. В томатном соусе с овощным рататуем (баклажан, цукини, болгарский перец, красный лук) базиликом, печеными томатами черри и сыром Пармезан.", 4, 399, 390, "ravioli_s_krevetkami"));
        dishes.add(new Dish("Равиоли ла карне", "Итальянские мясные равиоли в сливочном соусе с белыми грибами,беконом, кедровыми орешками, базиликом и сыром Пармезан.", 4, 369, 410, "ravioli_s_gribami_f275"));
        dishes.add(new Dish("Рататуй с киноа", "Оригинальное овощное блюдо из цукини, баклажанов, болгарского перца и красного лука с ароматными итальянскими травами и томатным соусом. Подаётся с киноа.", 4, 269, 190, "ratatuy"));
        dishes.add(new Dish("Паста с говядиной и овощами", "Фетучине с говядиной, болгарским перцем и стручковой фасолью в соусе Демигляс. ", 4, 259, 380, "pasta_s_govyadinoy_i_ovoschami"));
        dishes.add(new Dish("Паста Болоньезе", "Мясной соус Болоньезе с пассерованным луком, чесноком, соусами Неаполитано и Барбекью, свежими томатами, классическим сыром Пармезан и базиликом.", 4, 410, 289, "pasta_bolneze"));

        dishes.add(new Dish("Пицца портобелло", "Итальянская ветчина со свежими шампиньонами и маслинами, томатным соусом Помадоро и тёртым сыром Моцарелла.", 5, 299, 440, "pitstsa_portobello"));
        dishes.add(new Dish("Пепперони", "Настоящие колбаски Пепперони со свежими томатами, маслинами и каперсами, приправленные соусом Помадоро и нежным сыром Моцарелла.", 5, 399, 490, "pitstsa_pepperoni_chili_f346"));
        dishes.add(new Dish("4 сыра", "Сочетание сливочного сыра Моцарелла, терпкого Дор-Блю, Пармезана и твёрдого сыра, приправленное томатным соусом Помадоро.", 5, 430, 339, "pitstsa_4_syira_f298"));

        dishes.add(new Dish("Лимонный чизкейк", "Нежный десерт из воздушного сырного мусса с лимонным кремом.", 6, 249, 115, "chizkeyk"));
        dishes.add(new Dish("Крепвиль", "Нежнейший десерт из тонких блинчиков с восхитительным кремом из сыра Маскарпоне. Украшен свежей клубникой.", 6, 269, 210, "desertkrepvil"));
        dishes.add(new Dish("Мьеле", "Медовый торт с нежным сметанным кремом. Украшен грецким орехом в карамели.", 6, 269, 190, "desertmele"));

        dishes.add(new Dish("Чёрный чай", "Сочетание лучших индийских и цейлонских сортов с цитрусовыми нотками бергамота.", 7, 279, 225, "blacktea"));
        dishes.add(new Dish("Зелёный чай", "Зелёный чай, кубики ананаса и папайи, кусочки вишни, малины и красной смородины, лепестки подсолнечника.", 7, 279, 225, "greentea"));
        dishes.add(new Dish("Латте-макиато", "Итальянское macchia обозначает маленькое Пятнышко кофе, остающееся на Поверхности молочной Пены", 7, 139, 250, "image88"));
        dishes.add(new Dish("Капучино-роял", " Нежный кофе с молоком и лёгкой крепостью ликёра Бейлис. ", 7, 145, 180, "cappuccino"));

        dishes.add(new Dish("Морс брусничный", "Натуральный брусничный морс", 8, 155, 1000, "mors"));
        dishes.add(new Dish("Апельсиновый сок", "Свежевыжатый апельсиновый сок.", 8, 716, 1000, "svejevyijatyiy"));
        dishes.add(new Dish("Пина-Колада", "Ананасовый сок, сливки, кокосовый сироп, ананас и взбитые сливки", 8, 229, 300, "recept_koktejlja_pina_kolada"));
        dishes.add(new Dish("Мохито", "Лайм, БонАква, мята, сахар.", 8, 199, 125, "mohito"));
        return dishes;
    }

    private ArrayList<KategoriaDish> initKategories() {
        ArrayList<KategoriaDish> kategories = new ArrayList<KategoriaDish>();
        kategories.add(new KategoriaDish(1, "Салаты"));
        kategories.add(new KategoriaDish(2, "Закуски"));
        kategories.add(new KategoriaDish(3, "Супы"));
        kategories.add(new KategoriaDish(4, "Горячее"));
        kategories.add(new KategoriaDish(5, "Пицца"));
        kategories.add(new KategoriaDish(6, "Десерты"));
        kategories.add(new KategoriaDish(7, "Чай/Кофе"));
        kategories.add(new KategoriaDish(8, "Напитки"));

        return kategories;
    }
}
