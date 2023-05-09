package curso.spring.controller;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Component
public class ReportUtil implements Serializable {

    public  byte[] gerarRelatorio(List listDados,
                                  String relatorio,
                                  ServletContext servletContext) throws Exception{

        JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listDados);

        String caminhoJasper = servletContext.getRealPath("relatorios") +
                File.separator + relatorio + ".jasper";

        JasperPrint impriessoraJasper = JasperFillManager
                .fillReport(caminhoJasper, new HashMap<>(), jrbc);
        return JasperExportManager.exportReportToPdf(impriessoraJasper);

    }

}
