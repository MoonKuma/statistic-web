package com.sincetimes.statisticweb.service.translate;

import com.sincetimes.statisticweb.dao.file.ConfDao;
import com.sincetimes.statisticweb.model.ConfFile;
import com.sincetimes.statisticweb.model.Translation;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MoonKuma
 * @since 2019/4/19
 */

@Service
public class ItemTranslateService implements TranslateService<String, String> {

    private Translation<String,String> itemTranslator = new Translation<String,String>();

    @Autowired
    public ItemTranslateService(ConfDao confDao) {
        ClassPathResource resource = new ClassPathResource("item_price_trans.txt");
        ConfFile confFile = confDao.getConfMap(resource,",",0);
        // "1" is the price map, "2" is the translation map
        HashMap<String,String> confTrans= confFile.getConfMap().get("2");
        itemTranslator.initTranslation(confTrans,"NonExist");
    }

    @Override
    public String getValue(String key) {
        return itemTranslator.translateStay(key);
    }

    @Override
    public Map<String, String> getValueMap() {
        return itemTranslator.getTranslationTable();
    }
}
