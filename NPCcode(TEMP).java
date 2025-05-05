
import models.NPC;
import models.things.relations.Quest;


//abigail
ArrayList<Item> favorites = new ArrayList<>();
ArrayList<Item> possibleGifts = new ArrayList<>();
ArrayList<String> responses = new ArrayList<>();
Quest quest1 = new Quest(, , );
Quest quest2 = new Quest(, , );
Quest quest3 = new Quest(, , );
//normal(spring)
responses.add("“Oh, hey. Taking a break from work?“");
responses.add("“Oh, hi! Do you ever hang out at the cemetery? It's a peaceful place to spend some time alone.”");
responses.add("“Ugh... I'm not in a good mood right now.”");
responses.add("“Hey. Sorry in advance if I say anything rude. I didn't get much sleep last night. What do you want?”");
responses.add("“The fresh mountain air is nice on a day like this. I wonder if the frogs will make an appearance soon.”");
//summer
responses.add("“My pet guinea pig, David, just hates this hot weather. He's fussy.”");
responses.add("“The air is so thick with honey and nectar all summer. I almost feel dizzy.”");
responses.add("“Ew, I hope I don't get a tan this summer.”");
responses.add("“I'm looking forward to fall... the cool mountain breeze, the swirling red petals, the smell of mushrooms... *sigh*”");
responses.add("“Do you ever get an urge to go exploring?”");
//fall
responses.add("“I need to stretch my legs and get some fresh air today.”");
responses.add("“The air is so thick with honey and nectar all summer. I almost feel dizzy.”");
responses.add("“I can't wait to see some pumpkins this year. The spirit's eve festival happens at the end of the season.)
 It's pretty low-key compared to what you'd see in Zuzu City, but for a small town like this it's a lot of fun.”");
responses.add("“Do you have any scarecrows on your farm?”");
responses.add("“Are you growing pumpkins on your farm this year? Save one for me!”");
//winter
responses.add("“Well, fall is over... But I like winter, too!”");
responses.add("“It's just too cold to go outside much. But I do enjoy building a snowgoon.”");
responses.add("“Hi. Is it boring to be a farmer during the winter?”");
responses.add("“It must be nice not having crops to worry about this time of year.”");
responses.add("“It's so cold, I wish we had a hot cup of cocoa to share.”");
private NPC abigail = new NPC("abigail" , "Student" , quest1 , quest2 , quest3 , favorites , responses , possibleGifts , 1);
