package com.anemortalkid.csl.pagescrub;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestPageScrub {

	public static void main(String[] args) {
		try {
			Document csldoc = Jsoup.connect("http://cstarleague.com/sc2/teams/1494").userAgent("Mozilla").get();
			if(csldoc != null)
			{
				Element roster = csldoc.getElementById("roster");
				Elements divs = roster.getElementsByTag("div");
				System.out.println("Divs: " + divs.size());
				
				Elements RowGroups = roster.getElementsByAttributeValue("class", "row group");
				System.out.println("RowGroups:" + RowGroups.size());
				
				for(Element rowGroup : RowGroups)
				{
					Elements anchor = rowGroup.getElementsByTag("a");
					System.out.println("Anchor:" + anchor);
					for(Element aTag : anchor)
					{
						String playerName = aTag.text();
						System.out.println("Player Name:" + playerName);
						Attributes aTagAttributes = aTag.attributes();
						for(Attribute attr : aTagAttributes)
						{
							if(attr.getKey().equals("class"))
							{
								String classData = attr.getValue();
								int hyphen = classData.indexOf('-');
								String race = classData.substring(hyphen+1);
								System.out.println("race:" + race);
							}
						}
					}
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
