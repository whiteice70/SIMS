import java.util.Scanner;//导入用于获取键盘输入的包
import java.util.ArrayList;//导入用于构建集合的包
import java.util.Random;

public class Stu {
    //###############主函数#################
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        ArrayList<StuUser> uList= getUser();
        ArrayList<Student> list = getStudents();
        logChoose (list,uList,sc);
    }
    //初始页面
    public static void log(){
        System.out.println("****************学生管理系统*****************");
        System.out.println("1.登录系统");
        System.out.println("2.新用户，注册并登录");
        System.out.println("3(&其他输入).退出该系统");
        System.out.println("请输入你的选择：");
    }
    //初始页面功能
    public static void logChoose(ArrayList<Student> list, ArrayList<StuUser> uList,Scanner sc){
        String temp;
        do{
            log();
            temp=sc.next();
            switch(temp){
                case "1":
                    login(list,uList,sc);
                    break;
                case "2":
                    logon(list,uList,sc);
                    break;
                default:
                    temp="exit";
                    break;
            }
        }while(!temp.equals("exit"));
    }
    //注册页面
    public static void logon(ArrayList<Student> list, ArrayList<StuUser> uList,Scanner sc){
        System.out.println("*******************注册********************");
        System.out.println("请输入用户名：");
        String name=sc.next();
        if(!checkName(uList,name)){
            return;
        }
        System.out.println("请输入密码：");
        String password=sc.next();
        System.out.println("请输入确认密码：");
        String password1=sc.next();
        if(!password.equals(password1)){

            System.out.println("密码不一致！");
            return;
        }
        System.out.println("请输入身份证号：");
        String idCard=sc.next();
        if(!checkId(idCard)){
            return;
        }
        System.out.println("请输入电话号码：");
        String phone=sc.next();
        if(!checkPhone(phone)){
            return;
        }
        String code=createCode();
        System.out.println("验证码:\t"+code);
        System.out.println("请输入验证码:");
        String codeInput=sc.next();
        if(!codeInput.equals(code)){
            System.out.println("验证码错误！");
            return;
        }
        StuUser s=new StuUser(password,idCard,phone,name);
        uList.add(s);
        choose(list,sc,s);
    }
    //生成验证码
    public static String createCode(){
        Random r=new Random();
        StringBuilder sb=new StringBuilder();
        char[] crr=new char[52];
        for (int i = 0; i < 26; i++) {
            crr[2*i]=(char)(i+65);
            crr[2*i+1]=(char)(i+97);
        }
        for (int i = 0; i < 4; i++) {
            sb.append(crr[r.nextInt(0,52)]);
        }
        sb.insert(r.nextInt(0,5),r.nextInt(0,10));
        return sb.toString();
    }
    //判断手机号是否合法
    public static boolean checkPhone(String phone){
        if(phone.length()!=11){
            System.out.println("手机号码长度必须为11位！");
            return false;
        }
        if(phone.charAt(0)=='0'){
            System.out.println("不能以‘0’开头！");
            return false;
        }
        return true;
    }
    //判断身份证号是否合法
    public static boolean checkId(String idCard){
        if(idCard.length()!=18){
            System.out.println("身份证号长度必须为18位！");
            return false;
        }
        if(idCard.charAt(0)=='0'){
            System.out.println("不能以‘0’开头！");
            return false;
        }
        int contNumber=0;
        for (int i = 0; i < idCard.length()-1; i++) {
            if('0'<=idCard.charAt(i)||idCard.charAt(i)<='9'){
                contNumber++;
            }
        }
        if(contNumber!=17){
            System.out.println("前17位必须是数字！");
            return false;
        }
        if('0'<=idCard.charAt(17)||idCard.charAt(17)<='9'){
            return true;
        }
        if(idCard.charAt(17)=='X'||idCard.charAt(17)=='x'){
            return true;
        }
        System.out.println("身份证最后一位必须位数字或大小写字母‘x’");
        return false;
    }
    //判断用户名是否合法
    public static boolean checkName(ArrayList<StuUser> uList,String name){
        if(name.length()<3){
            System.out.println("用户名过短！");
            return false;
        }else if(name.length()>15){
            System.out.println("用户名过长！");
            return false;
        }
        int contNumber=0;
        int contLetter=0;
        for (int i = 0; i < name.length(); i++) {
            if('a'<name.charAt(i)&&name.charAt(i)<'z'){
                contLetter++;
            }else if('A'<name.charAt(i)&&name.charAt(i)<'Z'){
                contLetter++;
            }else if('0'<name.charAt(i)&&name.charAt(i)<'9'){
                contNumber++;
            }
        }
        if(contNumber+contLetter<name.length()){
            System.out.println("用户名必须由字母或数字加字母的组合构成！");
            return false;
        }
        if(contNumber==name.length()){
            System.out.println("用户名不能由纯数字构成！");
            return false;
        }
        for (StuUser stuUser : uList) {
            if(stuUser.getName().equals(name)){
                System.out.println("用户名已存在！");
                return false;
            }
        }
        return true;
    }
    //登录页面
    private static void login(ArrayList<Student> list, ArrayList<StuUser> uList,Scanner sc){
        System.out.println("*******************登录********************");
        System.out.println("请输入用户名:");
        String name=sc.next();
        System.out.println("请输入用户密码:");
        String password=sc.next();
        int digit=checkUser(uList,name);
        if(digit==-1){
            System.out.println("用户名不存在！");
            return;
        }
        if(!uList.get(digit).getPassword().equals(password)){
            System.out.println("密码错误！");
        }
        StuUser s=uList.get(digit);
        choose(list,sc,s);
    }
    //检验用户名是否存在，如果存在，返回对应集合索引，否则返回int值-1
    public static int checkUser(ArrayList<StuUser> uList,String name){
        for (int i = 0; i < uList.size(); i++) {
            if(uList.get(i).getName().equals(name)){
                return i;
            }
        }
        return -1;
    }
    //初始定义一些管理员账号
    private static ArrayList<StuUser> getUser(){
        ArrayList<StuUser> uList=new ArrayList<>();
        uList.add(new StuUser("123456about","4204861800010015050","18542007676","user1"));
        uList.add(new StuUser("123456after","5214561800073415050","18121186999","user2"));
        return uList;
    }
    //初始定义的一些用于测试的数据。
    private static ArrayList<Student> getStudents() {
        ArrayList<Student> list=new ArrayList<>();//定义了一个集合用于存储数据
        list.add(new Student("k001","小王",19,"湖北"));
        list.add(new Student("k002","小明",18,"河南"));
        list.add(new Student("k003","小张",20,"北京"));
        return list;
    }
    //选择功能
    public static void choose(ArrayList<Student> list,Scanner sc,StuUser s){
        String temp;
        do{
            order(s);
            temp=sc.next();
            switch(temp){//接收键盘录入的字符串，并判断
                case "1":
                    addStu(list,sc);
                    break;
                case "2":
                    deleteStu(list,sc);
                    break;
                case "3":
                    editStu(list,sc);
                    break;
                case "4":
                    queryStu(list,sc);
                    break;
                case "5":
                    showAll(list);
                    break;
                case "6":
                    showUser(s);
                    break;
                case "7":
                    return;
                default:
                    temp="exit";
                    break;
            }
        }while(!temp.equals("exit"));
        System.exit(0);
    }
    //打印用户信息
    public static void showUser(StuUser s){
        System.out.println("用户名:\t"+s.getName());
        System.out.println("电话号码:\t"+s.getPhoneNumber());
        System.out.println("生日;\t"+s.getIdCard().substring(6,10)+"年"+s.getIdCard().substring(10,12)+"月"+s.getIdCard().substring(12,14)+"日");
        char gender;
        if((int)s.getIdCard().charAt(16)%2==0){
            gender='女';
        }else{
            gender='男';
        }
        System.out.println("性别:\t"+gender);
    }
    //主菜单
    public static void order(StuUser s){
        System.out.println("*****************学生管理系统******************");
        System.out.print("*****************");
        System.out.println("当前用户为："+s.getName());
        System.out.println("1:添加学生");
        System.out.println("2:删除学生");
        System.out.println("3:修改学生");
        System.out.println("4:查询学生");
        System.out.println("5:输出全部学生信息");
        System.out.println("6.个人信息");
        System.out.println("7:退出到登录页面");
        System.out.println("8(&其他输入).退出系统");
        System.out.println("请输入你的选择：");
    }
    //遍历集合，判断id是否存在，如果存在，返回id所在Student对象的集合索引，如不存在，返回int值-1
    public static int checkId(ArrayList<Student> list,String id){
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getId().equals(id)){//判断集合索引i指向的对象的成员属性id是否与指定的id相同
                return i;//如果相同，返回该成员变量的索引
            }
        }
        return -1;//如果遍历集合结束，认定集合中没有满足条件的对象，返回int型-1
    }
    //添加学生
    public static void addStu(ArrayList<Student> list,Scanner sc){
        System.out.println("请输入学生id:");
        String id=sc.next();
        if(checkId(list,id)!=-1){
            System.out.println("该id已经存在！");
            return;
        }
        System.out.println("请输入学生姓名:");
        String name=sc.next();
        System.out.println("请输入学生年龄:");
        int year=sc.nextInt();
        System.out.println("请输入学生家庭住址");
        String address=sc.next();
        list.add(new Student(id,name,year,address));
    }
    //删除学生
    public static void deleteStu(ArrayList<Student> list,Scanner sc){
        System.out.println("请输入学生id:");
        String id=sc.next();
        int digit=checkId(list,id);
        if(digit==-1){
            System.out.println("该id不存在！");
            return;
        }
        list.remove(digit);
        System.out.println("已删除id为"+"\t"+id+"的学生信息！");
    }
    //修改学生
    public static void editStu(ArrayList<Student> list,Scanner sc){
        System.out.println("请输入学生id:");
        String id=sc.next();
        int digit=checkId(list,id);
        if(digit==-1){
            System.out.println("该id不存在！");
            return;
        }
        System.out.println("请输入更新的学生信息:");
        System.out.println("请输入更新后的学生姓名:");
        list.get(digit).setName(sc.next());
        System.out.println("请输入更新后的学生年龄:");
        list.get(digit).setYear(sc.nextInt());
        System.out.println("请输入更新后的学生住址:");
        list.get(digit).setAddress(sc.next());
        System.out.println("学生信息更新成功！");
    }
    //查询学生
    public static void queryStu(ArrayList<Student> list,Scanner sc){
        System.out.println("请输入学生id:");
        String id=sc.next();
        int digit=checkId(list,id);
        if(digit==-1){
            System.out.println("该id不存在！");
            return;
        }
        Student s=list.get(digit);
        System.out.println("id"+'\t'+'\t'+"name"+'\t'+"year"+'\t'+"address");
        showStu(s);
    }
    //输出对象信息
    public static void showStu(Student s){
        System.out.println(s.getId()+'\t'+s.getName()+'\t'+'\t'+s.getYear()+'\t'+'\t'+s.getAddress());
    }
    public static void showAll(ArrayList<Student> list){
        System.out.println("id"+'\t'+'\t'+"name"+'\t'+"year"+'\t'+"address");
        for(Student s:list){
            System.out.println(s.getId()+'\t'+s.getName()+'\t'+'\t'+s.getYear()+'\t'+'\t'+s.getAddress());
        }
    }
}
