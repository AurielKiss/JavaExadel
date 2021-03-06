package java.beans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.dao.EmployeeDAO;
import java.entity.Employee;
import java.util.*;

@ManagedBean
@ViewScoped
public class EmplEditBean { //backing bean

    private Map<String, List<String>> citiesMap;

    private Employee employee;

    @ManagedProperty(value = "#{employeeDAOSQLImpl}")
    private EmployeeDAO employeeDao;

    @PostConstruct
    public void init() {
        initCities();

        Map<String, String> params = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap();
        String id = params.get("id");
        System.out.println("id = " + id);
        try {
            editEmployee(Integer.valueOf(id));
        } catch (NumberFormatException e) {
            addNewEmployee();
        }
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    private void addNewEmployee() {
        System.out.println("ADD NEW EMPLOYEE");
        setEmployee(new Employee());
    }

    private void editEmployee(int id) {
        System.out.println("EDIT EMPLOYEE WITH ID: " + id);
        setEmployee(employeeDao.load(id));
    }

    public String save() {
        try {
            employeeDao.saveEmployee(getEmployee());
        } catch (Exception e) {
            //addErrorMessage(e.getMessage());
            return "";
        }
        return "index.xtml";
    }

    public EmployeeDAO getEmployeeDao() {
        return employeeDao;
    }

    public void setEmployeeDao(EmployeeDAO employeeDao) {
        this.employeeDao = employeeDao;
    }

    public Map<String, List<String>> getCitiesMap() {
        return citiesMap;
    }

    public void setCitiesMap(Map<String, List<String>> citiesMap) {
        this.citiesMap = citiesMap;
    }

    public void initCities() {
        citiesMap = new TreeMap<>();

        List<String> list = new ArrayList<>();
        list.add("Bajoru");
        list.add("Tvereciaus");
        list.add("Tulpiu");
        citiesMap.put("Vilnius", list);

        list = new ArrayList<>();
        list.add("Taikos");
        list.add("Fifu");
        list.add("Zveriu");
        citiesMap.put("Kaunas", list);

        list = new ArrayList<>();
        list.add("Karklu");
        list.add("Varskes");
        list.add("Saldainiu");
        citiesMap.put("Klaipeda", list);
    }

    public Set<String> getCities() {
        return getCitiesMap().keySet();
    }
}
