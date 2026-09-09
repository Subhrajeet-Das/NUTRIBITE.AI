package com.nutribite.ai.config;

import com.nutribite.ai.model.enums.DietType;
import com.nutribite.ai.nutrition.entity.Food;
import com.nutribite.ai.nutrition.model.MealType;
import com.nutribite.ai.nutrition.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class FoodDataLoader implements CommandLineRunner {
    private final FoodRepository repository;
    private static final int BATCH = 250;

    @Override @Transactional
    public void run(String... args) throws Exception {
        if (repository.count() > 0) return;
        var resource = new ClassPathResource("data/indian_foods.csv");
        List<Food> batch = new ArrayList<>(BATCH);
        try (CSVParser parser = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build().parse(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            for (CSVRecord r : parser) {
                try {
                    Food food = Food.builder()
                            .usdaId(text(r,"usdaId")).name(text(r,"name")).category(text(r,"category"))
                            .mealType(MealType.valueOf(text(r,"mealType").toUpperCase()))
                            .dietType(DietType.valueOf(text(r,"dietType").toUpperCase()))
                            .calories(number(r,"calories",0)).protein(number(r,"protein",0)).carbs(number(r,"carbs",0)).fat(number(r,"fat",0))
                            .fiber(number(r,"fiber",0)).sugar(number(r,"sugar",0)).sodium(number(r,"sodium",0)).servingSize(number(r,"servingSize",100))
                            .servingUnit(text(r,"servingUnit")).vegetarian(bool(r,"vegetarian")).vegan(bool(r,"vegan")).glutenFree(bool(r,"glutenFree"))
                            .dairyFree(bool(r,"dairyFree")).nutFree(bool(r,"nutFree")).soyFree(bool(r,"soyFree")).build();
                    batch.add(food);
                    if(batch.size()==BATCH){repository.saveAll(batch);batch.clear();}
                } catch(Exception ignored) { }
            }
        }
        if(!batch.isEmpty()) repository.saveAll(batch);
    }
    private String text(CSVRecord r,String k){String v=r.get(k);return v==null||v.isBlank()?null:v.trim();}
    private Double number(CSVRecord r,String k,double d){try{String v=text(r,k);return v==null?d:Double.parseDouble(v);}catch(Exception e){return d;}}
    private Boolean bool(CSVRecord r,String k){String v=text(r,k);return v!=null&&(v.equalsIgnoreCase("true")||v.equals("1"));}
}
