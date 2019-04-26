package com.sincetimes.statisticweb.dao.file;

import com.sincetimes.statisticweb.model.ConfFile;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * @author MoonKuma
 * @since 2019/4/19
 *
 * Read in config file
 * Conf file:
 * 1. The first column will always be regarded as index
 * 2. Each of rest columns will be put into a separate map (HashMap<String,String>)
 * 3. Conf file allows header form the first line, or it will use ${columnId} to indicate map from each column
 * 4. Example of input file
 * ItemId,Price,Name
 * 10030,2000,ShipPaperPieces
 * 10050,1500,PlanePaperPieces
 * 5. Example output
 * {"Price":{"10030":"2000","10050":"1500"},
 * "Name":{"10030":"ShipPaperPieces","10050":"PlanePaperPieces"}}
 * 5. Single column also works, where value==key
 * UID
 * 70890111234
 * 70885673212
 * 70990812345
 * result: {"UID":{"70890111234":"70890111234", "70885673212":"70885673212"}}
 *
 */

@Repository
public class ConfDao {

    public ConfFile getConfMap(ClassPathResource resource){
        ConfFile conf = new ConfFile();
        conf.setResource(resource);
        writeMap(conf);
        return conf;
    }

    public ConfFile getConfMap(ClassPathResource resource,  int headline){
        ConfFile conf = new ConfFile();
        conf.setResource(resource);
        conf.setHeadline(headline);
        writeMap(conf);
        return conf;
    }

    public ConfFile getConfMap(ClassPathResource resource, String delimiter){
        ConfFile conf = new ConfFile();
        conf.setResource(resource);
        conf.setDelimiter(delimiter);
        writeMap(conf);
        return conf;
    }


    public ConfFile getConfMap(ClassPathResource resource, String delimiter, int headline){
        ConfFile conf = new ConfFile();
        conf.setResource(resource);
        conf.setDelimiter(delimiter);
        conf.setHeadline(headline);
        writeMap(conf);
        return conf;
    }


    private void writeMap(@NotNull ConfFile conf){
        ClassPathResource resource = conf.getResource();
        String delimiter = conf.getDelimiter();
        int headline = conf.getHeadline();
        HashMap<String,HashMap<String,String>> confMap = conf.getConfMap();
        HashMap<Integer,String> headerMap = conf.getHeaderMap();
        int recordLength = 0;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
            String record = null;
            while ((record = br.readLine()) != null) {
                if (!"".equals(record.trim())) {
                    String[] recordArray = record.split(Pattern.quote(delimiter));
                    String itemId = recordArray[0].trim();
                    // line number
                    if (headerMap.keySet().size()==0){
                        recordLength = recordArray.length;
                    }

                    // one column version
                    if (recordArray.length==1 && recordArray.length==recordLength){
                        // load header
                        if (headerMap.keySet().size()==0){
                            if(headline==1){
                                headerMap.put(0, recordArray[0].trim());
                                continue;
                            }else{
                                headerMap.put(0, String.valueOf(0));
                            }
                        }
                        // load content
                        int index = 0;
                        HashMap<String,String> tmpConfMap = confMap.get(headerMap.get(index))==null?new HashMap<String,String>():confMap.get(headerMap.get(index));
                        String value = recordArray[index].trim();
                        tmpConfMap.put(itemId, value);
                        confMap.put(headerMap.get(index),tmpConfMap);
                    }

                    // more than one columns
                    if (recordArray.length>1 && recordArray.length==recordLength){
                        // load header
                        if (headerMap.keySet().size()==0){
                            if(headline==1){
                                for(int index=1;index<recordArray.length;index++){
                                    headerMap.put(index, recordArray[index].trim());
                                }
                                // skip reading this line if header==1
                                continue;
                            }else{
                                for(int index=1;index<recordArray.length;index++){
                                    headerMap.put(index, String.valueOf(index));
                                }
                            }
                        }
                        // load content
                        for(int index=1;index<recordArray.length;index++){
                            HashMap<String,String> tmpConfMap = confMap.get(headerMap.get(index))==null?new HashMap<String,String>():confMap.get(headerMap.get(index));
                            String value = recordArray[index].trim();
                            tmpConfMap.put(itemId, value);
                            confMap.put(headerMap.get(index),tmpConfMap);
                        }
                    }


                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        conf.setConfMap(confMap);
        conf.setHeaderMap(headerMap);
        System.out.println(conf);

    }

}
