package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistance.InsuranceDao;
import ar.edu.itba.paw.models.Insurance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class InsuranceServiceImplTest {

    @Autowired
    private InsuranceServiceImpl insuranceServiceImpl;

    private static final String INSURANCE_NAME = "OSDE FOR HIPPIES";

    private Insurance insurance;

    @Autowired
    private InsuranceDao insuranceDao;

    @Test
    public void testGetInsuranceByName() {
        insurance = Mockito.mock(Insurance.class);
        when(insuranceDao.findInsuranceByName(INSURANCE_NAME)).thenReturn(insurance);
        when(insurance.getName()).thenReturn(INSURANCE_NAME);

        Insurance insuranceReturned = insuranceServiceImpl.getInsuranceByName(INSURANCE_NAME);

        assertEquals(insuranceReturned.getName(),INSURANCE_NAME);
    }
}
