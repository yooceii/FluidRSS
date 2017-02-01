package com.myapp.yooceii.fluidrss;

import android.test.InstrumentationTestCase;

import com.myapp.yooceii.fluidrss.Utils.DomainUtils;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest{
    @Test
    public void test() throws Exception {
        SyndFeed syndFeed=new SyndFeedInput().build(new XmlReader(new URL("http://cn.engadget.com/rss.xml")));
        System.out.println(syndFeed.getEntries().get(1).getTitle());
    }
}