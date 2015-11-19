/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.itfox.test;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author belchiorpalma
 */
public class CreateTrigger {
    private String[] colPF = new String[]{"PLACA","MUNICIPIO","UF","RENAVAM","SITUACAOVEICULO","ULTIMAATUALIZACAO","CHASSI","REMARCACAOCHASSI","MARCA","ANOFABRICACAO","ANOMODELO","TIPOVEICULO","COR","ESPECIE","CATEGORIA","COMBUSTIVEL","CAPC_CARGA","PROCEDENCIA","CAPC_PASSAGEIROS","MOTOR","TIPOMONTAGEM","CARROCERIA","POTENCIA","CILINDRADA","CAIXA_CAMBIO","NUM_CARROCERIA","CAP_MAX_TRACAO","AGENTEFINANCEIRO","NOMEPROPRIETARIO","TIPOCNPJPROPRIETARIO","CPFCNPJPROPRIETARIO","NOME","SG_SEXO","DT_NASC","ESTADO_CIVIL","NO_MAE","TP_LOGR","NO_LOGR","NU_LOGR","NO_COMPL","NO_BAIRRO","NO_CIDADE","SG_ESTADO","NU_CEP","DDD","TELEFONE","DDD1","TELEFONE1","DDD2","TELEFONE2","DDD3","TELEFONE3","DDD4","TELEFONE4","DDD5","TELEFONE5","DDD_CELULAR1","CELULAR1","DDD_CELULAR2","CELULAR2","DDD_CELULAR3","CELULAR3","DDD_CELULAR4","CELULAR4","EMAIL","QTD_OCORRENCIA_CCF","SITUACAO","IDADE","SIGNO","CNPJ_PART_SOC","CARGO_PART_SOC","DT_ENTRADA_PART_SOC","NU_PART_SOC","DATA_OBITO","FLAG_OBITO","PPE","RG","EMISSOR_RG","UF_RG","CODIGO_CBO","CBO","RENDA_MENSAL","RESTRICAO_BANCO","RESTRICAO_AGENCIA","RESTRICAO_MOTIVO","NO_SOBRENOME","BOLSA_FAMILIA"};
    private String[] colPJ = new String[]{"PLACA","MUNICIPIO","UF","RENAVAM","SITUACAOVEICULO","ULTIMAATUALIZACAO","CHASSI","REMARCACAOCHASSI","MARCA","ANOFABRICACAO","ANOMODELO","TIPOVEICULO","COR","ESPECIE","CATEGORIA","COMBUSTIVEL","CAPC_CARGA","PROCEDENCIA","CAPC_PASSAGEIROS","MOTOR","TIPOMONTAGEM","CARROCERIA","POTENCIA","CILINDRADA","CAIXA_CAMBIO","NUM_CARROCERIA","CAP_MAX_TRACAO","AGENTEFINANCEIRO","NOMEPROPRIETARIO","TIPOCNPJPROPRIETARIO","CPFCNPJPROPRIETARIO","RAZAO_SOCIAL","NOME_FANTASIA","DT_ABERTURA","SITUACAO_RECEITA","ATIVIDADE_ECONOMICA_SECUNDARIA","CO_ATIVIDADE_ECONOMICA_SEC","SITE","NO_LOGR","NU_LOGR","NO_COMPL","NO_BAIRRO","NO_CIDADE","SG_ESTADO","NU_CEP","CO_ATIVIDADE_ECONOMICA","CO_NATUREZA_JURIDICA","ATIVIDADE_ECONOMICA","NATUREZA_JURIDICA","DDD1","TELEFONE1","DDD2","TELEFONE2","DDD3","TELEFONE3","DDD4","TELEFONE4","DDD5","TELEFONE5","DDD_CELULAR1","CELULAR1","DDD_CELULAR2","CELULAR2","QTD_OCORRENCIA_CCF","EMAIL_1","EMAIL_2","EMAIL_3","NU_CPF1","NOME_SOCIO_DIRETOR1","CARGO1","EMAIL_SOCIO1","TP_LOGR_CONTATO1","NO_LOGR_CONTATO1","NU_LOGR_CONTATO1","NO_COMPL_CONTATO1","NO_BAIRRO_CONTATO1","NO_CIDADE_CONTATO1","SG_ESTADO_CONTATO1","NU_CEP_CONTATO1","DDD1_FIXO_SOCIO1","TEL1_FIXO_SOCIO1","DDD2_FIXO_SOCIO1","TEL2_FIXO_SOCIO1","DDD3_FIXO_SOCIO1","TEL3_FIXO_SOCIO1","DDD1_CEL_SOCIO1","TEL1_CEL_SOCIO1","DDD2_CEL_SOCIO1","TEL2_CEL_SOCIO1","NU_CPF2","NOME_SOCIO_DIRETOR2","CARGO2","EMAIL_SOCIO2","TP_LOGR_CONTATO2","NO_LOGR_CONTATO2","NU_LOGR_CONTATO2","NO_COMPL_CONTATO2","NO_BAIRRO_CONTATO2","NO_CIDADE_CONTATO2","SG_ESTADO_CONTATO2","NU_CEP_CONTATO2","DDD1_FIXO_SOCIO2","TEL1_FIXO_SOCIO2","DDD2_FIXO_SOCIO2","TEL2_FIXO_SOCIO2","DDD3_FIXO_SOCIO2","TEL3_FIXO_SOCIO2","DDD1_CEL_SOCIO2","TEL1_CEL_SOCIO2","DDD2_CEL_SOCIO2","TEL2_CEL_SOCIO2","NU_CPF3","NOME_SOCIO_DIRETOR3","CARGO3","EMAIL_SOCIO3","TP_LOGR_CONTATO3","NO_LOGR_CONTATO3","NU_LOGR_CONTATO3","NO_COMPL_CONTATO3","NO_BAIRRO_CONTATO3","NO_CIDADE_CONTATO3","SG_ESTADO_CONTATO3","NU_CEP_CONTATO3","DDD1_FIXO_SOCIO3","TEL1_FIXO_SOCIO3","DDD2_FIXO_SOCIO3","TEL2_FIXO_SOCIO3","DDD3_FIXO_SOCIO3","TEL3_FIXO_SOCIO3","DDD1_CEL_SOCIO3","TEL1_CEL_SOCIO3","DDD2_CEL_SOCIO3","TEL2_CEL_SOCIO3","SG_SEXO_SOCIO1","SG_SEXO_SOCIO2","SG_SEXO_SOCIO3","DT_NASC_SOCIO1","DT_NASC_SOCIO2","DT_NASC_SOCIO3","CNPJ_QSA1","DT_ENTRADA_SOC1","NU_PART_CAP_SOCIAL1","CNPJ_QSA2","DT_ENTRADA_SOC2","NU_PART_CAP_SOCIAL2","CNPJ_QSA3","DT_ENTRADA_SOC3","NU_PART_CAP_SOCIAL3","RESTRICAO_BANCO","RESTRICAO_AGENCIA","RESTRICAO_MOTIVO","EMAIL","NU_CPF_CNPJ1","NU_CPF_CNPJ2","NU_CPF_CNPJ3"};

    public String[] getColPF() {
        return colPF;
    }

    public String[] getColPJ() {
        return colPJ;
    }
    
    public StringBuilder showTrigger(String tablename, String[] columns){
        StringBuilder s = new StringBuilder();
        s.append("DELIMITER $$ \n");
        s.append("CREATE \n");
        s.append("  TRIGGER `"+tablename+"_BEFORE_UPDATE` BEFORE UPDATE \n");
        s.append("  ON `"+tablename+"` \n");
        s.append("  FOR EACH ROW BEGIN \n");
        s.append("  DECLARE AUX VARCHAR(255); \n");
        s.append("  -- Created by ITFOX - "+new Date().toLocaleString()+" \n\n");
        for(int i=0; i < columns.length; i++){
             s.append("-- "+columns[i]+" \n");
             s.append("SELECT CHANGE_VALUE(OLD."+columns[i]+",NEW."+columns[i]+") INTO AUX; \n");
             s.append("SET NEW."+columns[i]+" = AUX; \n\n");
        }
        s.append("  END$$ \n");
        s.append("DELIMITER ;");
        return s;
    }
    
    public StringBuilder createTable(String tablename,String[] columns, String[] primaryKey){
        StringBuilder s = new StringBuilder();
        s.append("CREATE TABLE "+tablename+"\n" +"(\n");
        for(int i=0; i < columns.length; i++){
             //s.append("-- "+columns[i]+" \n");
            String key="";
            for(int j=0; j < primaryKey.length; j++){
             //s.append("-- "+columns[i]+" \n");
                if(columns[i].equalsIgnoreCase(primaryKey[j].toLowerCase())){
                    key=" NOT ";
                }
            }
             s.append(columns[i]+" VARCHAR(254) "+key+ "NULL, \n");
             
        }
        
        // primary key
        s.append("PRIMARY KEY (");
        for(int i=0; i < primaryKey.length; i++){
             //s.append("-- "+columns[i]+" \n");
            if(i<primaryKey.length-1){
             s.append(primaryKey[i]+",");
            }else{
             s.append(primaryKey[i]+"");   
            }
             
        }
        s.append(")\n ");
        s.append(")ENGINE=InnoDB DEFAULT CHARSET=latin1;\n");
        return s;
    }
    
    public StringBuilder importFiles(String tablename, String[] columns){
        StringBuilder s = new StringBuilder();
        s.append("LOAD DATA LOCAL INFILE '/Users/belchiorpalma/Desktop/PJ_RETORNO.csv' \n");
        s.append("REPLACE INTO TABLE QUEST."+tablename+" \n");
        s.append("FIELDS TERMINATED BY ';' \n");
        s.append("OPTIONALLY ENCLOSED BY '\"' ESCAPED BY '\\\\' \n");
        s.append("LINES TERMINATED BY '\\n' \n");
        s.append("IGNORE 1 LINES \n");
        //s.append("  -- Created by ITFOX - "+new Date().toLocaleString()+" \n\n");
        s.append("(");
        String str="";
        for(int i=1; i < columns.length+1; i++){
             
             if(i<columns.length){
                str+="@col"+i+", ";
             }else{
                str+="@col"+i+", @col"+(i+1);  
             }
        }
        s.append(str);
        s.append(") set ");
        for(int i=0; i < columns.length; i++){
             
             if(i<columns.length-1){
                s.append(columns[i]+"=@col"+(1+i)+",");
             }else{
                s.append(columns[i]+"=@col"+(1+i)+"" );
             }
        }
        s.append(";");
        return s;
    }
    
    public StringBuilder insertOnDuplicate(String tablename, String[] columns){
        StringBuilder s = new StringBuilder();
       // s.append("LOAD DATA LOCAL INFILE '/Users/belchiorpalma/Desktop/PJ_RETORNO.csv' \n");
       // s.append("REPLACE INTO TABLE QUEST."+tablename+" \n");
       // s.append("FIELDS TERMINATED BY ';' \n");
       // s.append("OPTIONALLY ENCLOSED BY '\"' ESCAPED BY '\\\\' \n");
       // s.append("LINES TERMINATED BY '\\n' \n");
        s.append("ON DUPLICATE KEY UPDATE \n");
        //s.append("  -- Created by ITFOX - "+new Date().toLocaleString()+" \n\n");
       // s.append("(");
        String str="";
        for(int i=0; i < columns.length; i++){
             
             if(i<columns.length-1){
                str+=""+columns[i]+" = VALUES("+columns[i]+"), ";
             }else{
                str+=""+columns[i]+" = VALUES("+columns[i]+") ";  
             }
        }
        s.append(str);
        //s.append(") set ");
        
        s.append(";");
        return s;
    }
    public static void main(String[] args){
        CreateTrigger c = new CreateTrigger();
        String tablename="PF_RETORNO";
        //System.out.println(c.createTable(tablename,c.getColPJ(), new String[]{"CHASSI", "CPFCNPJPROPRIETARIO"}));
        //System.out.println(c.showTrigger("PJ_RETORNO", c.getColPJ()));
        System.out.println(c.importFiles(tablename, c.colPF));
        //for(int i=1;i<155;i++){
        //    System.out.print("@col"+i+",");
        //}
        //System.out.println(c.insertOnDuplicate(tablename,c.colPF));
    }
}
