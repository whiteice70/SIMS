//定义了一个Javabean类，用于记录学生信息；
public class Student {
    private String id;
    private String name;
    private int year;
    private String address;
    //构造方法
    public Student() {
    }

    public Student(String id, String name, int year, String address) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.address = address;
    }
    //对象属性获取&设置方法
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
