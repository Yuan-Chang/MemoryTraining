package com.example;


import java.util.Date;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class GreenDAOGenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.teeoda.memorytraining.global.GreenDAO");

        addTrainingHistory(schema);

        new DaoGenerator().generateAll(schema, "app/src/main/java");
    }

    private static void addTrainingHistory(Schema schema) {
        Entity entry = schema.addEntity("TrainingHistory");
        entry.addIdProperty();
        entry.addStringProperty("type");
        entry.addIntProperty("total");
        entry.addIntProperty("correctNum");
        entry.addStringProperty("timeSpent");
        entry.addDateProperty("date");
    }
}
