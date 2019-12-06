package ar.edu.itba.paw.webapp.dto.insurance;

import ar.edu.itba.paw.models.Insurance;
import ar.edu.itba.paw.models.InsurancePlan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsuranceDTO {
    // private Integer id;
    private String name;
    // private InsurancePlanListDTO plans;
    private List<String> plans;
    public InsuranceDTO () {
    }

    public InsuranceDTO (Insurance insurance){
        this.name = insurance.getName();
        this.plans = new ArrayList<>();
        for (InsurancePlan ip : insurance.getPlans()){
            this.plans.add(ip.getPlan());
        }
    }

    public InsuranceDTO (String insuranceName, List<String> insurancePlans) {
        this.name = insuranceName;
        this.plans = insurancePlans;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPlans() {
        return plans;
    }

    public void setPlans(List<String> plans) {
        this.plans = plans;
    }

    @Override
    public String toString() {
        return "InsuranceDTO{" +
                ", name='" + name + '\'' +
                ", plans=" + plans +
                '}';
    }

    public static List<InsuranceDTO> insurancePlanMapping (List<InsurancePlan> insurancePlans) {
        Map<String, List<String>> map= new HashMap<>();
        for (InsurancePlan ip : insurancePlans) {
            String insurance = ip.getInsurance().getName();
            if (!map.containsKey(insurance)){
                List<String> insurancePlansList = new ArrayList<>();
                map.put(insurance, insurancePlansList);
            }
            List<String> toAdd = map.get(insurance);
            toAdd.add(ip.getPlan());
        }
        List<InsuranceDTO> insuranceDTOList = new ArrayList<>();
        for (String insurance : map.keySet()){
            insuranceDTOList.add(new InsuranceDTO(insurance, map.get(insurance)));
        }
        return insuranceDTOList;
    }

}
