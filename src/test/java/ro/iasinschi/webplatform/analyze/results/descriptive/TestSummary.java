package ro.iasinschi.webplatform.analyze.results.descriptive;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * File TestSummary.java
 * TODO: Document me
 * <p/>
 * Created by Adrian Iasinschi (aiasinschi@gmail.com) on 2/9/2015 8:43 AM.
 */
public class TestSummary {

    @Test
    public void testJSONConvert(){
        Summary summary = new Summary();
        summary.setVariables(Arrays.asList("id", "name", "age", "city"));
        summary.addOutputLine(Arrays.asList("11", "John", "23", "New York"));
        summary.addOutputLine(Arrays.asList("12", "Mary", "20", "Pittsburgh"));
        summary.addOutputLine(Arrays.asList("13", "George", "21", "Seattle"));
        summary.addOutputLine(Arrays.asList("14", "Sam", "22", "Miami"));
        System.out.println(summary.toJSONResult());
        Assert.assertTrue("Nothing to check here just yet", true);
    }
}
