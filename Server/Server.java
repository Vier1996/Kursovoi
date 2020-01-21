package Server;
import Tools.Deshifr;
import Tools.Shifr;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

public class Server {
    public static Connection connection;
    public static Statement statement;

    private static class Data_base{

        String Get_name_of_Ogrz() throws SQLException {
            StringBuilder S = new StringBuilder();
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery("select name_of_organiz from Organization");
            while (set.next()) {
                S.append(set.getString("name_of_organiz")).append("/");
            }
            return S.toString();
        }

        double roundD(double Q){
            int O = (int)Math.round(Q * 1000);
            Q = (double)O / 1000;
            return Q;
        }

        void Show_organiz(BufferedWriter W, BufferedReader R) throws SQLException, IOException {
            String OUTPUT = "";
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery("select* from Organization\n" +
                    "inner join Income on Organization.Org_ID = Income.Income_Orgz_ID\n" +
                    "inner join Expenses on Organization.Org_ID = Expenses.Expenses_Orgz_ID\n" +
                    "inner join Concurents on Organization.Org_ID = Concurents.Concurents_Orgz_ID\n" +
                    "inner join Activity on Organization.Org_ID = Activity.Activity_Orgz_ID\n" +
                    "inner join Activies on Income.Income_Orgz_ID = Activies.Activies_Incom_ID\n" +
                    "inner join Shareholder on Expenses.Expenses_Orgz_ID = Shareholder.Shareholder_Expens_ID");

            while (set.next()) {
                OUTPUT += "Название организации: " + set.getString("name_of_organiz")
                + " | Тип организации: " + set.getString("Type_org") + " | Имя начальника: " + set.getString("name_of_chairman") + " | Возраст: "
                + set.getInt("Org_Old") + ", Локация: " + set.getString("Country") + " | Кол. персонала: " + set.getInt("Personal")
                + " | Накопления организации: " + set.getInt("AccFonds") + "/" + "Сфера делятельности: " + set.getString("Occupation")
                + " | Тип продукции: " + set.getString("type_of_prod") + "/" + "Название конкурента: " + set.getString("Concurents_name")
                + " | Стаж конурента: " + set.getInt("Concurents_age") + "/" + "Выручка с продукции: " + set.getInt("RevenuePr")
                + " | Прибыль: " + set.getInt("Earnings") + " | Оказание аренды: " + set.getInt("Rent_own_act") + " | Брэндовый налог: "
                + set.getInt("Branding_tax") + "/" + "Название актива: " + set.getString("Name_Act") + " | Стоимость актива: " + set.getInt("Cost_Act")
                + " | Возраст актива: " + set.getInt("Age_Act") + "/" + "Мелкие издержки: " + set.getInt("LowTerm") + " | Крупные издержки: "
                + set.getInt("HiTerm") + " | Налоги: " + set.getInt("Taxes") + " | Долги: " + set.getInt("Credits") + " | Затраты на улучшения: "
                + set.getInt("Improving") + "/" + "Количество акционеров: " + set.getInt("Amount_SH") + " | Девиденты: " + set.getInt("Devidents")
                + " | Рыночная стоимость акций: " + set.getInt("CoE") + "//";
            }

            W.write(OUTPUT + "\n");
            W.flush();

            R.readLine(); // waiting
        }

        void Red_Organization(String type_of_red, int Index, int type_of_operation) throws SQLException {
            String[] Indx_Znh = type_of_red.split("-");
            int TypeOper = Integer.parseInt(Indx_Znh[0]);
            statement = connection.createStatement();

            switch (TypeOper) {
                case 1:
                    statement.execute("update Organization set name_of_organiz = '" + Indx_Znh[1] + "' where Org_ID = " + Integer.toString(Index));
                    break;
                case 2:
                    statement.execute("update Organization set Type_org = '" + Indx_Znh[1] + "' where Org_ID = " + Integer.toString(Index));
                    break;
                case 3:
                    statement.execute("update Organization set name_of_chairman = '" + Indx_Znh[1] + "' where Org_ID = " + Integer.toString(Index));
                    break;
                case 4:
                    statement.execute("update Organization set Org_old = '" + Indx_Znh[1] + "' where Org_ID = " + Integer.toString(Index));
                    break;
                case 5:
                    statement.execute("update Organization set Country = '" + Indx_Znh[1] + "' where Org_ID = " + Integer.toString(Index));
                    break;
                case 6:
                    statement.execute("update Organization set Personal = '" + Indx_Znh[1] + "' where Org_ID = " + Integer.toString(Index));
                    break;
                case 7:
                    statement.execute("update Organization set AccFonds = '" + Indx_Znh[1] + "' where Org_ID = " + Integer.toString(Index));
                    break;
                default: break;
            }
        }

        void Red_Activity(String type_of_red, int Index, int type_of_operation) throws SQLException {
            String[] Indx_Znh = type_of_red.split("-");
            int TypeOper = Integer.parseInt(Indx_Znh[0]);
            statement = connection.createStatement();
            switch (TypeOper) {
                case 1:
                    statement.execute("update Activity set Occupation = '" + Indx_Znh[1] + "' where Activity_ID = " + Integer.toString(Index));
                    break;
                case 2:
                    statement.execute("update Activity set type_of_prod = '" + Indx_Znh[1] + "' where Activity_ID = " + Integer.toString(Index));
                    break;
                default: break;
            }

        }

        void Red_Concurents(String type_of_red, int Index, int type_of_operation) throws SQLException {
            String[] Indx_Znh = type_of_red.split("-");
            int TypeOper = Integer.parseInt(Indx_Znh[0]);
            statement = connection.createStatement();
            switch (TypeOper) {
                case 1:
                    statement.execute("update Concurents set Concurents_name = '" + Indx_Znh[1] + "' where Concurents_ID = " + Integer.toString(Index));
                    break;
                case 2:
                    statement.execute("update Concurents set Concurents_age = '" + Indx_Znh[1] + "' where Concurents_ID = " + Integer.toString(Index));
                    break;
                default: break;
            }

        }

        void Red_Income(String type_of_red, int Index, int type_of_operation) throws SQLException {
            String[] Indx_Znh = type_of_red.split("-");
            int TypeOper = Integer.parseInt(Indx_Znh[0]);
            statement = connection.createStatement();
            switch (TypeOper) {
                case 1:
                    statement.execute("update Income set RevenuePr = '" + Indx_Znh[1] + "' where Income_ID = " + Integer.toString(Index));
                    break;
                case 2:
                    statement.execute("update Income set Earnings = '" + Indx_Znh[1] + "' where Income_ID = " + Integer.toString(Index));
                    break;
                case 3:
                    statement.execute("update Income set Rent_own_act = '" + Indx_Znh[1] + "' where Income_ID = " + Integer.toString(Index));
                    break;
                case 4:
                    statement.execute("update Income set Branding_tax = '" + Indx_Znh[1] + "' where Income_ID = " + Integer.toString(Index));
                    break;
                default: break;
            }
        }

        void Red_Activies(String type_of_red, int Index, int type_of_operation) throws SQLException {
            String[] Indx_Znh = type_of_red.split("-");
            int TypeOper = Integer.parseInt(Indx_Znh[0]);
            statement = connection.createStatement();
            switch (TypeOper) {
                case 1:
                    statement.execute("update Activies set Name_Act = '" + Indx_Znh[1] + "' where Activies_ID = " + Integer.toString(Index));
                    break;
                case 2:
                    statement.execute("update Activies set Cost_Act = '" + Indx_Znh[1] + "' where Activies_ID = " + Integer.toString(Index));
                    break;
                case 3:
                    statement.execute("update Activies set Age_Act = '" + Indx_Znh[1] + "' where Activies_ID = " + Integer.toString(Index));
                    break;
                default: break;
            }
        }

        void Red_Expanses(String type_of_red, int Index, int type_of_operation) throws SQLException {
            String[] Indx_Znh = type_of_red.split("-");
            int TypeOper = Integer.parseInt(Indx_Znh[0]);
            statement = connection.createStatement();
            switch (TypeOper) {
                case 1:
                    statement.execute("update Expenses set LowTerm = '" + Indx_Znh[1] + "' where Expenses_ID = " + Integer.toString(Index));
                    break;
                case 2:
                    statement.execute("update Expenses set HiTerm = '" + Indx_Znh[1] + "' where Expenses_ID = " + Integer.toString(Index));
                    break;
                case 3:
                    statement.execute("update Expenses set Taxes = '" + Indx_Znh[1] + "' where Expenses_ID = " + Integer.toString(Index));
                    break;
                case 4:
                    statement.execute("update Expenses set Credits = '" + Indx_Znh[1] + "' where Expenses_ID = " + Integer.toString(Index));
                    break;
                case 5:
                    statement.execute("update Expenses set Improving = '" + Indx_Znh[1] + "' where Expenses_ID = " + Integer.toString(Index));
                    break;
                default: break;
            }
        }

        void Red_Shareholders(String type_of_red, int Index, int type_of_operation) throws SQLException {
            String[] Indx_Znh = type_of_red.split("-");
            int TypeOper = Integer.parseInt(Indx_Znh[0]);
            statement = connection.createStatement();
            switch (TypeOper) {
                case 1:
                    statement.execute("update Shareholder set Amount_SH = '" + Indx_Znh[1] + "' where Shareholder_ID = " + Integer.toString(Index));
                    break;
                case 2:
                    statement.execute("update Shareholder set Devidents = '" + Indx_Znh[1] + "' where Shareholder_ID = " + Integer.toString(Index));
                    break;
                case 3:
                    statement.execute("update Shareholder set CoE = '" + Indx_Znh[1] + "' where Shareholder_ID = " + Integer.toString(Index));
                    break;
            }
        }

        void Redaction(BufferedReader R, BufferedWriter W, String login) throws IOException, SQLException {
            W.write(Get_name_of_Ogrz() + "\n");
            W.flush();

            String input_redaction = R.readLine(); // ждет от окна
            String[] Operations = input_redaction.split("/");

            if(Operations[0].isEmpty() || Operations[0] == null) {

            }
            else {
                int redaction_index = 0;
                statement = connection.createStatement();
                ResultSet set = statement.executeQuery("select Org_ID from Organization where name_of_organiz = '" + Operations[0] + "'");
                while (set.next()) {
                    redaction_index = set.getInt(1);
                }

                for (int i = 1; i < Operations.length; i++) {

                    if (Integer.parseInt(Operations[i].split("-")[0]) != 0) {
                        switch (i) {
                            case 1:
                                Red_Organization(Operations[i], redaction_index, 1);
                                break;
                            case 2:
                                Red_Activity(Operations[i], redaction_index, 1);
                                break;
                            case 3:
                                Red_Concurents(Operations[i], redaction_index, 1);
                                break;
                            case 4:
                                Red_Income(Operations[i], redaction_index, 1);
                                break;
                            case 5:
                                Red_Activies(Operations[i], redaction_index, 1);
                                break;
                            case 6:
                                Red_Expanses(Operations[i], redaction_index, 1);
                                break;
                            case 7:
                                Red_Shareholders(Operations[i], redaction_index, 1);
                                break;
                            default:
                                break;
                        }
                    }
                }
                System.out.println(login + ": изменил значения в базе данных.");
            }
        }

        void Cleaner(int indx) throws SQLException {
            statement = connection.createStatement();
            statement.execute("delete from Shareholder\n" +
                    "where Shareholder_Expens_ID = " + indx);
            statement.execute("delete from Activies\n" +
                    "where Activies_Incom_ID = " + indx);
            statement.execute("delete from Concurents\n" +
                    "where Concurents_Orgz_ID = " + indx);
            statement.execute("delete from Activity\n" +
                    "where Activity_Orgz_ID = " + indx);
            statement.execute("delete from Income\n" +
                    "where Income_Orgz_ID = " + indx);
            statement.execute("delete from Expenses\n" +
                    "where Expenses_ID = " + indx);
            statement.execute("delete from Organization\n" +
                    "where Org_ID = " + indx);
        }

        void Addtion_to_DB(String[] INPUT) throws SQLException {
            int NewIndx = Get_name_of_Ogrz().split("/").length + 1;
            String[] to_Orz = INPUT[0].split("/"), to_Actv = INPUT[1].split("/"),
                     to_Con = INPUT[2].split("/"), to_Inc = INPUT[3].split("/"),
                     to_Exp = INPUT[4].split("/"), to_Actvs = INPUT[5].split("/"),
                     to_Shr = INPUT[6].split("/");
            statement = connection.createStatement();
            statement.execute("insert into Organization\n" +
                    "values('"+ to_Orz[0] +"', '" + to_Orz[2] + "', " + to_Orz[3] + ", '" +  to_Orz[4] + "', " + to_Orz[5]+  ", " + to_Orz[6] + ", '" + to_Orz[1]+ "')");

            ResultSet set = statement.executeQuery("select Org_ID from Organization where name_of_organiz = '" + to_Orz[0] + "'");
            while(set.next()) {
                NewIndx = set.getInt(1);
            }

            statement.execute("insert into Activity\n" +
                    "values('" + to_Actv[0] + "', '" + to_Actv[1] + "', " + NewIndx + ")");
            statement.execute("insert into Concurents\n" +
                    "values('" + to_Con[0] + "', " + to_Con[1] + ", " + NewIndx + ")");
            statement.execute("insert into Income\n" +
                    "values(" + to_Inc[0] + ", " + to_Inc[1] + ", " + to_Inc[2] + ", " + to_Inc[3] + ", " + NewIndx + ")");
            statement.execute("insert into Expenses\n" +
                    "values(" + to_Exp[0] + ", " + to_Exp[1] + ", " + to_Exp[2] + ", " + to_Exp[3] + ", " + to_Exp[4] + ", " + NewIndx + ")");
            statement.execute("insert into Activies\n" +
                    "values('" + to_Actvs[0] + "', " + to_Actvs[1] + ", " + to_Actvs[2] + ", " + NewIndx + ")");
            statement.execute("insert into Shareholder\n" +
                    "values(" + to_Shr[0] + ", " + to_Shr[1] + ", " + to_Shr[2] + ", " + NewIndx + ")");
        }

        void Activies(String[] NEW) throws SQLException {
            int Index = 0;
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery("select Org_ID from Organization where name_of_organiz = '" + NEW[0] + "'");
            while(set.next()) {
                Index = set.getInt(1);
            }

            statement.execute("insert into Activies\n" +
                    "values('" + NEW[1] + "', " + NEW[2] + ", " + NEW[3] + ", " + Index + ")");
        }

        void NewAddition(BufferedReader R, BufferedWriter W, String login) throws IOException, SQLException {
            String IN = R.readLine();
            if(!IN.equals("back")) {
                String[] INPUT = IN.split("//");
                Addtion_to_DB(INPUT);
                System.out.println(login + ": добавил данные в базу данных.");
            }
        }

        void Del_Bank(BufferedReader R, BufferedWriter W, String login) throws IOException, SQLException {
            String Output = Get_name_of_Ogrz();
            int Indx = 0;
            W.write(Output + "\n");
            W.flush();
            String name = R.readLine();
            if(!name.equals("back")) {
                statement = connection.createStatement();
                ResultSet set = statement.executeQuery("select Org_ID from Organization where name_of_organiz = '" + name + "'");
                while(set.next()) {
                    Indx = set.getInt(1);
                }

                Cleaner(Indx);
                System.out.println(login);
            }
        }

        void Model(BufferedWriter W, int Am, String T) throws SQLException, IOException {
            statement = connection.createStatement(); int id = 0;
            String Output = "";
            String[] names = Get_name_of_Ogrz().split("/");

            int[] Current_Actvs = new int[Am], Common_Actvs = new int[Am], Nersp_Dohod = new int[Am],
                  Dohod_before_taxes = new int[Am], CoE = new int[Am], RevenuePR = new int[Am], LowTerm = new int[Am], ID = new int[Am];
            double[] Koef = new double[Am];
            for(int i = 0; i < Am; i++) {
                Current_Actvs[i] = 0; Common_Actvs[i] = 0; Nersp_Dohod[i] = 0;
                Dohod_before_taxes[i] = 0; CoE[i] = 0; RevenuePR[i] = 0; LowTerm[i] = 0; Koef[i] = 0;
            }

            ResultSet set = statement.executeQuery("select Org_ID from Organization");
            while(set.next()) {
                ID[id] = set.getInt("Org_ID");
                id++;
            }

            for(int i = 0; i < Am; i++) { /*Cur_Actvs*/
                set = statement.executeQuery("select Cost_Act from Activies where Age_Act <= 1 and Activies_Incom_ID = " + ID[i]);
                while(set.next()) {
                    Current_Actvs[i] += set.getInt("Cost_Act");
                }

                set = statement.executeQuery("select Cost_Act from Activies where Activies_Incom_ID = " + ID[i]);
                while(set.next()) {
                    Common_Actvs[i] += set.getInt("Cost_Act");
                }

                set = statement.executeQuery("select AccFonds from Organization where Org_ID = " + ID[i]);
                while(set.next()) {
                    Nersp_Dohod[i] += set.getInt("AccFonds");
                }

                set = statement.executeQuery("select Earnings, Rent_own_act from Income where Income_Orgz_ID = " + ID[i]);
                while(set.next()) {
                    Dohod_before_taxes[i] += set.getInt("Earnings") + set.getInt("Rent_own_act");
                }

                set = statement.executeQuery("select CoE from Shareholder where Shareholder_Expens_ID = " + ID[i]);
                while(set.next()) {
                    CoE[i] += set.getInt("CoE");
                }

                set = statement.executeQuery("select RevenuePr from Income where Income_Orgz_ID = " + ID[i]);
                while(set.next()) {
                    RevenuePR[i] += set.getInt("RevenuePr");
                }

                set = statement.executeQuery("select LowTerm from Expenses where Expenses_Orgz_ID = " + ID[i]);
                while(set.next()) {
                    LowTerm[i] += set.getInt("LowTerm");
                }

                Koef[i] += 1.2 * ((double)Current_Actvs[i] / (double)Common_Actvs[i]) + 1.4 * ((double)Nersp_Dohod[i] / (double)Common_Actvs[i]) +
                        3.3 * ((double)Dohod_before_taxes[i] / (double)Common_Actvs[i]) + 0.6 * ((double)CoE[i] / (double)LowTerm[i]) +
                        ((double)RevenuePR[i] / (double)Common_Actvs[i]);
                Koef[i] = roundD(Koef[i]);
            }

            if(T.equals("Succes")) {
                String ZamS = "";
                double ZamD = 0;

                for(int i = 0; i < Am - 1; i++) {
                    for(int j = i + 1; j < Am ; j++) {
                        if(Koef[j] > Koef[i]) {

                            ZamD = Koef[j];
                            Koef[j] = Koef[i];
                            Koef[i] = ZamD;

                            ZamS = names[j];
                            names[j] = names[i];
                            names[i] = ZamS;

                        }
                    }
                }

                Output += names[0] + " " + Double.toString(Koef[0]) + "/" + names[Am - 1] + " " + Double.toString(Koef[Am - 1]);

                W.write(Output + "\n");
                W.flush();
            }
            if(T.equals("Model")) {
                for (int i = 0; i < Am; i++) {
                    Output += names[i] + " " + Integer.toString(Current_Actvs[i]) + " " + Integer.toString(Common_Actvs[i]) + " " + Integer.toString(Nersp_Dohod[i]) + " " +
                            Integer.toString(Dohod_before_taxes[i]) + " " + Integer.toString(CoE[i]) + " " + Integer.toString(RevenuePR[i]) + " " +
                            Integer.toString(LowTerm[i]) + " " + Double.toString(Koef[i]) + "/";
                }

                W.write(Output + "\n");
                W.flush();
            }
            if(T.equals("Advice")) {
                String ZamS = "";
                double ZamD = 0;
                int ZamI = 0, Count = 0;
                for(int i = 0; i < Am - 1; i++) {
                    for(int j = i + 1; j < Am ; j++) {
                        if(Koef[j] > Koef[i]) {

                            ZamD = Koef[j];
                            Koef[j] = Koef[i];
                            Koef[i] = ZamD;

                            ZamS = names[j];
                            names[j] = names[i];
                            names[i] = ZamS;

                            ZamI = Current_Actvs[j];
                            Current_Actvs[j] = Current_Actvs[i];
                            Current_Actvs[i] = ZamI;

                            ZamI = Common_Actvs[j];
                            Common_Actvs[j] = Common_Actvs[i];
                            Common_Actvs[i] = ZamI;

                            ZamI = Nersp_Dohod[j];
                            Nersp_Dohod[j] = Nersp_Dohod[i];
                            Nersp_Dohod[i] = ZamI;

                            ZamI = Dohod_before_taxes[j];
                            Dohod_before_taxes[j] = Dohod_before_taxes[i];
                            Dohod_before_taxes[i] = ZamI;

                            ZamI = CoE[j];
                            CoE[j] = CoE[i];
                            CoE[i] = ZamI;

                            ZamI = RevenuePR[j];
                            RevenuePR[j] = RevenuePR[i];
                            RevenuePR[i] = ZamI;

                            ZamI = LowTerm[j];
                            LowTerm[j] = LowTerm[i];
                            LowTerm[i] = ZamI;
                        }
                    }
                }

                for(int i = 0; i < Am; i++) {
                    if(Count >= 3)
                        break;
                   if(Koef[i] >= 2.7) {
                       Output += names[i] + " " + Integer.toString(Current_Actvs[i]) + " " + Integer.toString(Common_Actvs[i]) + " " + Integer.toString(Nersp_Dohod[i]) + " " +
                               Integer.toString(Dohod_before_taxes[i]) + " " + Integer.toString(CoE[i]) + " " + Integer.toString(RevenuePR[i]) + " " +
                               Integer.toString(LowTerm[i]) + " " + Double.toString(Koef[i]) + "/";
                       Count++;
                   }
                }

                W.write(Output + "\n");
                W.flush();
            }
        }

        void Liqufy(BufferedWriter W, BufferedReader R) throws SQLException, IOException {
            W.write(Get_name_of_Ogrz() + "\n");
            W.flush();

            int IndxBuyer = 0, IndxSel = 0;

            String IN = R.readLine();
            if(!IN.equals("back")) {
                String[] NAMES = R.readLine().split(" ");
                statement = connection.createStatement();
                ResultSet set = statement.executeQuery("select Org_ID from Organization where name_of_organiz = '" + NAMES[0] + "'");
                while(set.next()) {
                    IndxSel = set.getInt(1);
                }
                set = statement.executeQuery("select Org_ID from Organization where name_of_organiz = '" + NAMES[1] + "'");
                while(set.next()) {
                    IndxBuyer = set.getInt(1);
                }

                statement.execute("update Activity set Activity_Orgz_ID = " + IndxBuyer + " where Activity_Orgz_ID = " + IndxSel);
                statement.execute("update Concurents set Concurents_Orgz_ID = " + IndxBuyer + " where Concurents_Orgz_ID = " + IndxSel);
                statement.execute("update Income set Income_Orgz_ID= " + IndxBuyer + " where Income_Orgz_ID = " + IndxSel);
                statement.execute("update Expenses set Expenses_Orgz_ID = " + IndxBuyer + " where Expenses_Orgz_ID = " + IndxSel);

                statement.execute("delete from Organization where Org_ID = " + IndxSel);
            }
        }

        void Save() throws SQLException, IOException {
            int Amount = Get_name_of_Ogrz().split("/").length, idx = 0;
            int[] Indx = new int[Amount];
            String SAVE = "";
            File file = new File("SaveDB.txt");
            BufferedWriter fr = new BufferedWriter(new FileWriter(file, false));

            statement = connection.createStatement();
            ResultSet set = statement.executeQuery("select Org_ID from Organization");
            while(set.next()) {
                Indx[idx] = set.getInt("Org_ID");
                idx++;
            }

            for(int i = 0; i < Amount; i++) {
                set = statement.executeQuery("select* from Organization as a\n" +
                        "inner join Income as b on a.Org_ID = b.Income_Orgz_ID\n" +
                        "inner join Expenses as c on a.Org_ID = c.Expenses_Orgz_ID\n" +
                        "inner join Concurents as d on a.Org_ID = d.Concurents_Orgz_ID\n" +
                        "inner join Activity as e on a.Org_ID = e.Activity_Orgz_ID\n" +
                        "inner join Activies as f on b.Income_Orgz_ID = f.Activies_Incom_ID\n" +
                        "inner join Shareholder as g on c.Expenses_Orgz_ID = g.Shareholder_Expens_ID\n" +
                        "where a.Org_ID = " + Indx[i]);

                while (set.next()) {
                        SAVE += set.getString("name_of_organiz") + " " + set.getString("name_of_chairman") + " " +
                                set.getString("Org_old") + " " +set.getString("Country") + " " +
                                set.getString("Personal") + " " +set.getString("AccFonds") + " " +
                                set.getString("Type_org") + "\n" + set.getString("Occupation") + " " +
                                set.getString("type_of_prod") + " " + (i + 1) + "\n" + set.getString("Concurents_name") + " " +
                                set.getString("Concurents_age") + " " + (i + 1) + "\n" + set.getString("RevenuePr") + " " +
                                set.getString("Earnings") + " " + set.getString("Rent_own_act") + " " + set.getString("Branding_tax") + " " +
                                (i + 1) + "\n" + set.getString("LowTerm") + " " + set.getString("HiTerm") + " " +
                                set.getString("Taxes") + " " + set.getString("Credits") + " " + set.getString("Improving") + " " +
                                (i + 1) + "\n" + set.getString("Name_Act") + " " + set.getString("Cost_Act") + " " +
                                set.getString("Age_Act") + " " + (i + 1) + "\n" + set.getString("Amount_SH") + " " + set.getString("Devidents") + " " +
                                set.getString("CoE") + " " + (i + 1) + "\n";
                }
            }

            fr.write(SAVE);
            fr.flush();
        }

        void Load() throws SQLException, IOException {
            int Amount = Get_name_of_Ogrz().split("/").length, idx = 0, table = 0, INX = 0, incINX = 0, expINX= 0;
            int[] Indx = new int[Amount];
            String mess = "";
            File file = new File("SaveDB.txt");
            BufferedReader fr = new BufferedReader(new FileReader(file));

            statement = connection.createStatement();
            ResultSet set = statement.executeQuery("select Org_ID from Organization");
            while(set.next()) {
                Indx[idx] = set.getInt("Org_ID");
                idx++;
            }

            for(int i = 0; i < Amount; i++) {
                Cleaner(Indx[i]);
            }

            while((mess = fr.readLine())!= null) {
                String[] Raz = mess.split(" ");
                if(table > 6)
                    table = 0;

                switch (table) {
                    case 0: statement.execute("insert into Organization\n" +
                            "values('" + Raz[0] + "', '" + Raz[1] + "', " + Integer.parseInt(Raz[2]) +
                            ", '" + Raz[3] + "', " + Integer.parseInt(Raz[4]) + ", " + Integer.parseInt(Raz[5]) +
                            ", '" + Raz[6] + "')");

                        set = statement.executeQuery("select Org_ID from Organization where name_of_organiz = '" + Raz[0] + "'");
                        while(set.next()) {
                            INX = set.getInt(1);
                        }

                    break;
                    case 1: statement.execute("insert into Activity\n" +
                            "values('" + Raz[0] + "', '" + Raz[1] + "', " + INX + ")"); break;
                    case 2: statement.execute("insert into Concurents\n" +
                            "values('" + Raz[0] + "', " + Integer.parseInt(Raz[1]) + ", " + INX + ")"); break;
                    case 3: statement.execute("insert into Income\n" +
                            "values(" + Integer.parseInt(Raz[0]) + ", " + Integer.parseInt(Raz[1]) +
                            ", " + Integer.parseInt(Raz[2]) + ", " + Integer.parseInt(Raz[3]) + ", " + INX + ")");

                        set = statement.executeQuery("select Income_ID from Income where Income_Orgz_ID = " + INX);
                        while(set.next()) {
                            incINX = set.getInt(1);
                        }
                    break;
                    case 4: statement.execute("insert into Expenses\n" +
                            "values(" + Integer.parseInt(Raz[0]) + ", " + Integer.parseInt(Raz[1]) +
                            ", " + Integer.parseInt(Raz[2]) + ", " + Integer.parseInt(Raz[3]) +
                            ", " + Integer.parseInt(Raz[4]) + ", " + INX + ")");

                        set = statement.executeQuery("select Expenses_ID from Expenses where Expenses_Orgz_ID = " + INX);
                        while(set.next()) {
                            expINX = set.getInt(1);
                        }
                    break;
                    case 5: statement.execute("insert into Activies\n" +
                            "values('" + Raz[0] + "', " + Integer.parseInt(Raz[1]) + ", " + Integer.parseInt(Raz[2]) +
                            ", " + incINX + ")"); break;
                    case 6: statement.execute("insert into Shareholder\n" +
                            "values(" + Integer.parseInt(Raz[0]) + ", " + Integer.parseInt(Raz[1]) + ", " + Integer.parseInt(Raz[2]) +
                            ", " + expINX + ")"); break;
                    default: break;
                }

                table++;
            }
            statement.execute("use model;");
            statement.execute("use Kursovoi;");
        }
    }

    private static class Com_menu extends Data_base{

        void list(BufferedWriter W, BufferedReader R, String login) throws IOException {
            System.out.println(login + ": выбрал просмотр базы данных.");
            File file = new File("testReg.txt");
            BufferedReader fr = new BufferedReader(new FileReader(file));
            String Mess = "", LIST = "";

            while((Mess = fr.readLine())!= null) {
                String[] DATA = Mess.split(" ");
                LIST += DATA[0] + " " + DATA[1] + " " + DATA[2] + "/";
            }
            W.write(LIST + "\n");
            W.flush();
            R.readLine();
        }

        void SHOW_DB(BufferedWriter W, BufferedReader R, String login) throws SQLException, IOException {
            System.out.println(login + ": выбрал просмотр базы данных.");
            Show_organiz(W, R);
        }
    }

    private static class Admin_menu extends Com_menu{
        BufferedReader Rd;
        BufferedWriter Wr;
        int LoV = 1;
        int input = 0;
        String login = "";

        void Adm_instr_red_user(BufferedReader R, BufferedWriter W) throws IOException {
            String Choice_ = R.readLine();
            switch (Choice_) {
                case "1": Add_new_u(R, W); break;
                case "2": Red_u(); break;
                case "3": Del_u(R); break;
                default: break;
            }
        }

        void Adm_instr_red_cmp(BufferedReader R, BufferedWriter W) throws IOException, SQLException {
            String Choice_ = R.readLine();

            switch (Choice_) {
                case "1": NewAddition(R, W, "Бог-" + login); break;
                case "2": Redaction(R, W, "Бог-" + login); break;
                case "3": Del_Bank(R, W, "Бог-" + login + ": удалил значение в базе данных."); break;
                default: break;
            }
        }

        void Adm_list(BufferedWriter W, BufferedReader R) throws IOException {
            System.out.println("Бог-" + login + ": вывел список пользователей программы.");
            list(Wr, Rd, "Пользователь-" + login);
        } //9

        void Add_new_u(BufferedReader R, BufferedWriter W) throws IOException {
            File file = new File("testReg.txt");
            BufferedWriter writerF = new BufferedWriter(new FileWriter(file, true));

            String IN = R.readLine();
            if(!IN.equals("back")) {
                String[] DATA = IN.split(" ");
                writerF.write(DATA[0] + " " + Shifr.SHFR(DATA[1]) + " " + DATA[2] + "\n");
                writerF.flush();
                writerF.close();
                System.out.println("Бог-" + login + ": добавил пользователя с логином: " + DATA[0]);
            }
            else {
                writerF.close();
            }
        }

        void Red_u() throws IOException {
            File file = new File("testReg.txt"),
                    file1 = new File("testReg.txt"),
                        file2 = new File("testReg.txt");

            BufferedReader fr1 = new BufferedReader(new FileReader(file));
            BufferedReader fr2 = new BufferedReader(new FileReader(file2));

            String[] Data, Usrs;
            String mess = "", Output = "", toWindow = "";
            int U = 0;

            while((mess = fr1.readLine())!= null) {
                U++;
                toWindow += mess + "/";
            }
            fr1.close();
            Wr.write(toWindow + U + "\n");
            Wr.flush();

            String IN = Rd.readLine();
            if(!IN.equals("back")) {
                Data = IN.split(" ");
                Usrs = new String[U];
                U = 0;
                while ((mess = fr2.readLine()) != null) {
                    Usrs[U] = mess;
                    U++;
                }
                fr2.close();

                BufferedWriter fr = new BufferedWriter(new FileWriter(file1, false));
                for (int i = 0; i < Usrs.length; i++) {
                    if (i == Integer.parseInt(Data[3])) {
                        Output += Data[0] + " " + Data[1] + " " + Data[2] + "\n";
                    } else
                        Output += Usrs[i] + "\n";
                }
                fr.write(Output);
                fr.flush();
                fr.close();
                System.out.println("Бог-" + login + ": изменил данные пользователя с логином: " + Data[0]);
            }
        }

        void Del_u(BufferedReader R) throws IOException {
            File file = new File("testReg.txt"),
                    file1 = new File("testReg.txt");
            BufferedReader fr1 = new BufferedReader(new FileReader(file));

            String[] Usrs;
            String mess = "", Output = "", toWindow = "";
            int U = 0;

            while((mess = fr1.readLine())!= null) {
                U++;
                toWindow += mess + "/";
            }
            fr1.close();
            Usrs = toWindow.split("/");

            Wr.write(toWindow + U + "\n");
            Wr.flush();

            int Indx = Integer.parseInt(R.readLine());

            BufferedWriter fr = new BufferedWriter(new FileWriter(file1, false));
            for(int i = 0; i < Usrs.length; i++) {
                if(i == Indx)
                    continue;
                Output += Usrs[i] + "\n";
            }
            fr.write(Output);
            fr.flush();
            fr.close();
            System.out.println("Бог-" + login + ": удалил пользователя с логином: " + Usrs[Indx].split(" ")[0]);
        }

        void Compare() throws IOException, SQLException {
            int Amount_of_orgz = Get_name_of_Ogrz().split("/").length;
            Model(Wr, Amount_of_orgz, "Model");
            System.out.println("Бог-" + login + ": выбрал сравнение организаций.");
        }

        private void EmergencyDestDB() throws SQLException, IOException {
            System.out.println("Бог-" + login + ": аварийно завершил работу программы и стер данные БД.");
            String valid = Rd.readLine();
            if(valid.equals("Yes")) {
                Wr.write("y\n");
                Wr.flush();
                statement = connection.createStatement();
                statement.execute("alter database Kursovoi set single_user with rollback immediate");
                statement.execute("use master;");
                statement.execute("DROP DATABASE Kursovoi;");
                System.exit(0);
            }
            else {
                Wr.write("n\n");
                Wr.flush();
            }
        }

        private void SaveDB() throws IOException, SQLException {
            Save();
            System.out.println("Бог-" + login + ": сделал сохранение БД.");
        }

        private void LoadDB() throws IOException, SQLException {
            Load();
            System.out.println("Бог-" + login + ": восстановил сохранение БД.");
        }

        void menu(BufferedReader R, BufferedWriter W, BufferedReader RF) throws IOException, SQLException { // Main Func
            File file = new File("testReg.txt");
            BufferedReader fr = new BufferedReader(new FileReader(file));
            String Mess = "";

            Rd = R;
            Wr = W;

            String L = Rd.readLine();
            login = L;

            boolean is_exist = false;
            while((Mess = fr.readLine())!= null) {
                String[] Chk = Mess.split(" ");
                if(Chk[0].equals(L)) {
                    is_exist = true;
                }
            }
            if(is_exist) {
                Wr.write("exist\n");
                Wr.flush();
            }
            else {
                Wr.write("noexist\n");
                Wr.flush();
            }

            input = Integer.parseInt(Rd.readLine());
            switch (input) {

                case 1: Adm_instr_red_user(R, W); break;
                case 2: Adm_instr_red_cmp(R, W); break;
                case 3: SHOW_DB(W, R, "Бог-" + login); break;
                case 5: Compare(); break;
                case 6: Adm_list(W,R); break;
                case 7: EmergencyDestDB(); break;
                case 8: SaveDB(); break;
                case 9: LoadDB(); break;
                default: LoV = 0; System.out.println(login + ": вышел из сети."); break;
            }

        }
    }

    private static class User_menu extends Com_menu{
        BufferedReader Rd;
        BufferedWriter Wr;
        int LoV = 2;
        int input = 0;
        String login = "";

        void List_info_comp() throws IOException, SQLException {
            SHOW_DB(Wr, Rd, "Пользователь-" + login);
        } //1

        void List() throws IOException {
            list(Wr, Rd, "Пользователь-" + login);
        } //

        void Request_for_app_comp() throws IOException, SQLException {
            NewAddition(Rd, Wr,"Пользователь-" + login + ": указал банкрота в БД.");
        } //3

        void Request_for_update_data() throws IOException, SQLException {
            Redaction(Rd, Wr, "Пользователь-" + login);
        } //4

        void Request_for_data_of_bankrots() throws SQLException, IOException {
            int Amount_of_orgz = Get_name_of_Ogrz().split("/").length;
            Model(Wr, Amount_of_orgz, "Model");
            System.out.println("Пользователь-" + login + ": вывел систему вероятности банкротства организаций.");
        } //5

        void Contract() throws IOException, SQLException {
            Liqufy(Wr, Rd);
            System.out.println("Пользователь-" + login + ": указал подписание контракта между организациями.");
        } //6

        void SelfDestroyer() throws IOException {

            File file = new File("testReg.txt"),
                    file1 = new File("testReg.txt");
            BufferedReader fr1 = new BufferedReader(new FileReader(file));

            String[] Usrs;
            String mess = "", user = "", Output = "";
            int U = 0;

            while((mess = fr1.readLine())!= null) {
                U++;
                user += mess + "/";
            }

            Usrs = user.split("/");

            String log = Rd.readLine();
            if(!log.equals("back")) {
                Wr.write("yes\n");
                Wr.flush();
                BufferedWriter fr = new BufferedWriter(new FileWriter(file1, false));
                for (String usr : Usrs) {
                    if (usr.split(" ")[0].equals(log))
                        continue;
                    Output += usr + "\n";
                }
                fr.write(Output);
                fr.flush();
                fr.close();

                /**/
                LoV = 0;
                System.out.println("Пользователь-" + login + ": удалил свой аккаунт.");
            }
            else {
                Wr.write("no\n");
                Wr.flush();
            }
        }

        void New_activies(BufferedWriter W, BufferedReader R) throws SQLException, IOException {
            String[] Input;
            W.write(Get_name_of_Ogrz() + "\n");
            W.flush();
            String IN = R.readLine();
            if(!IN.equals("back")) {
                Input = IN.split(" ");
                Activies(Input);
                System.out.println("Пользователь-" + login + ": дабавил новые данный в таблицу (Activies).");
            }
        } //7

        void Bankroting() throws IOException, SQLException {
            Del_Bank(Rd, Wr, "Пользователь-" + login);
        } //8

        void Compare() throws IOException, SQLException {
            int Amount_of_orgz = Get_name_of_Ogrz().split("/").length;
            Model(Wr, Amount_of_orgz, "Model");
            System.out.println("Пользователь-" + login + ": выбрал сравнение организаций.");
        }

        void Success() throws IOException, SQLException {
            int Amount_of_orgz = Get_name_of_Ogrz().split("/").length;
            Model(Wr, Amount_of_orgz, "Succes");
            System.out.println("Пользователь" + login + ": получил информацию о самой успешной и самой худшей организации.");
        }

        void AdvicesOnInvest() throws SQLException, IOException {
            int Amount_of_orgz = Get_name_of_Ogrz().split("/").length;
            Model(Wr, Amount_of_orgz, "Advice");
            System.out.println("Пользователь" + login + ": получил информацию о компаниях, в которые выгодно вложить деньги.");
        }

        void menu(BufferedReader R, BufferedWriter W, BufferedReader RF) throws IOException, SQLException { // Main Func
            File file = new File("testReg.txt");
            BufferedReader fr = new BufferedReader(new FileReader(file));
            String Mess = "";

            Rd = R;
            Wr = W;

            String L = Rd.readLine();
            login = L;
            boolean is_exist = false;
            while((Mess = fr.readLine())!= null) {
                String[] Chk = Mess.split(" ");
                if(Chk[0].equals(L)) {
                    is_exist = true;
                }
            }
            if(is_exist) {
                Wr.write("exist\n");
                Wr.flush();
            }
            else {
                Wr.write("noexist\n");
                Wr.flush();
            }

            input = Integer.parseInt(Rd.readLine());

            switch (input) {
                case 1: List_info_comp(); break;
                case 2: List(); break;
                case 3: Request_for_app_comp(); break;
                case 4: Request_for_update_data(); break;
                case 5: Request_for_data_of_bankrots(); break;
                case 6: New_activies(Wr, Rd); break;
                case 7: Contract(); break;
                case 8: Bankroting(); break;
                case 9: SelfDestroyer(); break;
                case 10: Success(); break;
                case 11: AdvicesOnInvest(); break;
                case 12: Compare(); break;
                default: LoV = 0; System.out.println(L + ": вышел из сети."); break;
            }

        }
    }

    private static class Serv extends Thread {

        private Socket sock;
        private BufferedReader readerF, reader;
        private BufferedWriter writerF, writer;
        private int Level_of_validation = 0;
        private boolean is_online = false;

        Serv(Socket S) throws IOException {
            this.sock = S;
            File file = new File("testReg.txt");
            this.readerF = new BufferedReader(new FileReader(file));
            this.writerF = new BufferedWriter(new FileWriter(file, true));

            this.reader = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(this.sock.getOutputStream()));

            this.is_online = true;
        }

        void Main_M() throws IOException {
            String Reg = reader.readLine();
            if(Reg.equals("1"))
                Registration();
            else
                if(Reg.equals("0"))
                    Autorization();
                else {
                    is_online = false;
                }
        }

        void Autorization() throws IOException {
            File f1 = new File("testReg.txt");
            BufferedReader timeRd1 = new BufferedReader(new FileReader(f1));

            String IN = reader.readLine();
            if(IN.equals("back") == true) {
                writer.write(Integer.toString(Level_of_validation) + " " + "-" + "\n");
                writer.flush();
                timeRd1.close();
            }
            else {
                String[] Valid = IN.split(" ");
                String Mess = "", Login = "-";

                while((Mess = timeRd1.readLine())!= null) {
                    String[] DATA = Mess.split(" ");
                    if(DATA[0].equals(Valid[0]) && Deshifr.DESHFR(DATA[1]).equals(Valid[1]) && Integer.parseInt(DATA[2]) == 0) {
                        Level_of_validation = 2;
                        Login = Valid[0];
                        System.out.println(Login + ": вошел в сеть.");
                        break;
                    }
                    if(DATA[0].equals(Valid[0]) && Deshifr.DESHFR(DATA[1]).equals(Valid[1]) && Integer.parseInt(DATA[2]) == 1) {
                        Level_of_validation = 1;
                        Login = Valid[0];
                        System.out.println(Login + ": вошел в сеть.");
                        break;
                    }
                }

                writer.write(Integer.toString(Level_of_validation) + " " + Login + "\n");
                writer.flush();
                timeRd1.close();
            }
        }

        void Registration() throws IOException {
            String Valid = this.reader.readLine();
            if(!Valid.equals("back")) {
                writerF.write(Valid + "\n");
                writerF.flush();
                System.out.println("Клиент зарегистрировался (LOGGIN:" + Valid.split(" ")[0] + ").");
            }
        }

        @Override
        public void run() {

            while(is_online) {

                switch (Level_of_validation) {
                    case 0:
                        try {
                            Main_M();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;

                    case 1:
                        //////////////////
                        Admin_menu Adm_m = new Admin_menu();
                        try {
                            Adm_m.menu(reader, writer, readerF);
                            Level_of_validation = Adm_m.LoV;
                        } catch (IOException | SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        //////////////////
                        User_menu Usr_m = new User_menu();
                        try {
                            Usr_m.menu(reader, writer, readerF);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        Level_of_validation = Usr_m.LoV;
                        break;

                    case 6: break;
                    default: break;
                }


            }
        }

    }

    public static Connection getConnection() throws IOException, SQLException {
        Properties properties = new Properties();

        try(InputStream in = Files.newInputStream(Paths.get("database.properties"))) {
            properties.load(in);
        }
        String URL = properties.getProperty("URL");

        return DriverManager.getConnection(URL, "root", "");
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        ServerSocket serverSocket = new ServerSocket(7000);
        System.out.println("Сервер запущен...");

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = getConnection();
        System.out.println("Соединение с БД выполнено успешно...");
        statement = connection.createStatement();
        statement.execute("use Kursovoi");

        while(true) {
            Socket clientSocket = serverSocket.accept();
            Serv SERVER = new Serv(clientSocket);
            SERVER.start();
        }
    }
}
