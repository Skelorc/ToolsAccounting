package wns.services;

import org.springframework.stereotype.Service;
import wns.aspects.ToLog;

@Service
public class ReportsService {

    @ToLog
    public void createTest()
    {
        System.out.println("Вызов из метода!");
        throw new NullPointerException("тестовая ошибка!");
    }

}
