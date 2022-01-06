import javafx.scene.image.Image;

/**
 * Class that stores constant values for the program
 * 
 * @author Darren Seubert
 */
public class Constants {
    public static final String playerCSVFilePath = "./src/PlayerFiles/players.csv";

    public static final String blackjackLogoFilePath = "./Images/blackjack_logo.png";
    public static final String cardBackFilePath = "./Images/Cards/card_back.png", // Card File Paths
                               cutCardFilePath = "./Images/Cards/cut.png",

                               clubAceFilePath = "./Images/Cards/Clubs/ace.png",
                               clubTwoFilePath = "./Images/Cards/Clubs/2.png",
                               clubThreeFilePath = "./Images/Cards/Clubs/3.png",
                               clubFourFilePath = "./Images/Cards/Clubs/4.png",
                               clubFiveFilePath = "./Images/Cards/Clubs/5.png",
                               clubSixFilePath = "./Images/Cards/Clubs/6.png",
                               clubSevenFilePath = "./Images/Cards/Clubs/7.png",
                               clubEightFilePath = "./Images/Cards/Clubs/8.png",
                               clubNineFilePath = "./Images/Cards/Clubs/9.png",
                               clubTenFilePath = "./Images/Cards/Clubs/10.png",
                               clubJackFilePath = "./Images/Cards/Clubs/jack.png",
                               clubQueenFilePath = "./Images/Cards/Clubs/queen.png",
                               clubKingFilePath = "./Images/Cards/Clubs/king.png",

                               diamondAceFilePath = "./Images/Cards/Diamonds/ace.png",
                               diamondTwoFilePath = "./Images/Cards/Diamonds/2.png",
                               diamondThreeFilePath = "./Images/Cards/Diamonds/3.png",
                               diamondFourFilePath = "./Images/Cards/Diamonds/4.png",
                               diamondFiveFilePath = "./Images/Cards/Diamonds/5.png",
                               diamondSixFilePath = "./Images/Cards/Diamonds/6.png",
                               diamondSevenFilePath = "./Images/Cards/Diamonds/7.png",
                               diamondEightFilePath = "./Images/Cards/Diamonds/8.png",
                               diamondNineFilePath = "./Images/Cards/Diamonds/9.png",
                               diamondTenFilePath = "./Images/Cards/Diamonds/10.png",
                               diamondJackFilePath = "./Images/Cards/Diamonds/jack.png",
                               diamondQueenFilePath = "./Images/Cards/Diamonds/queen.png",
                               diamondKingFilePath = "./Images/Cards/Diamonds/king.png",

                               heartAceFilePath = "./Images/Cards/Hearts/ace.png",
                               heartTwoFilePath = "./Images/Cards/Hearts/2.png",
                               heartThreeFilePath = "./Images/Cards/Hearts/3.png",
                               heartFourFilePath = "./Images/Cards/Hearts/4.png",
                               heartFiveFilePath = "./Images/Cards/Hearts/5.png",
                               heartSixFilePath = "./Images/Cards/Hearts/6.png",
                               heartSevenFilePath = "./Images/Cards/Hearts/7.png",
                               heartEightFilePath = "./Images/Cards/Hearts/8.png",
                               heartNineFilePath = "./Images/Cards/Hearts/9.png",
                               heartTenFilePath = "./Images/Cards/Hearts/10.png",
                               heartJackFilePath = "./Images/Cards/Hearts/jack.png",
                               heartQueenFilePath = "./Images/Cards/Hearts/queen.png",
                               heartKingFilePath = "./Images/Cards/Hearts/king.png",

                               spadeAceFilePath = "./Images/Cards/Spades/ace.png",
                               spadeTwoFilePath = "./Images/Cards/Spades/2.png",
                               spadeThreeFilePath = "./Images/Cards/Spades/3.png",
                               spadeFourFilePath = "./Images/Cards/Spades/4.png",
                               spadeFiveFilePath = "./Images/Cards/Spades/5.png",
                               spadeSixFilePath = "./Images/Cards/Spades/6.png",
                               spadeSevenFilePath = "./Images/Cards/Spades/7.png",
                               spadeEightFilePath = "./Images/Cards/Spades/8.png",
                               spadeNineFilePath = "./Images/Cards/Spades/9.png",
                               spadeTenFilePath = "./Images/Cards/Spades/10.png",
                               spadeJackFilePath = "./Images/Cards/Spades/jack.png",
                               spadeQueenFilePath = "./Images/Cards/Spades/queen.png",
                               spadeKingFilePath = "./Images/Cards/Spades/king.png";

    public static int cardPixelWidth = 80,
                      cardPixelHeight = 115,
                      shoePileXPos = 1050,
                      shoePileYPos = 25;

    public static Image backOfCardImage = new Image(cardBackFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        cutCardImage = new Image(cutCardFilePath, cardPixelWidth, cardPixelHeight, true, true),

                        clubAceImage = new Image(clubAceFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        clubTwoImage = new Image(clubTwoFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        clubThreeImage = new Image(clubThreeFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        clubFourImage = new Image(clubFourFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        clubFiveImage = new Image(clubFiveFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        clubSixImage = new Image(clubSixFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        clubSevenImage = new Image(clubSevenFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        clubEightImage = new Image(clubEightFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        clubNineImage = new Image(clubNineFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        clubTenImage = new Image(clubTenFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        clubJackImage = new Image(clubJackFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        clubQueenImage = new Image(clubQueenFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        clubKingImage = new Image(clubKingFilePath, cardPixelWidth, cardPixelHeight, true, true),

                        diamondAceImage = new Image(diamondAceFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        diamondTwoImage = new Image(diamondTwoFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        diamondThreeImage = new Image(diamondThreeFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        diamondFourImage = new Image(diamondFourFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        diamondFiveImage = new Image(diamondFiveFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        diamondSixImage = new Image(diamondSixFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        diamondSevenImage = new Image(diamondSevenFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        diamondEightImage = new Image(diamondEightFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        diamondNineImage = new Image(diamondNineFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        diamondTenImage = new Image(diamondTenFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        diamondJackImage = new Image(diamondJackFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        diamondQueenImage = new Image(diamondQueenFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        diamondKingImage = new Image(diamondKingFilePath, cardPixelWidth, cardPixelHeight, true, true),

                        heartAceImage = new Image(heartAceFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        heartTwoImage = new Image(heartTwoFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        heartThreeImage = new Image(heartThreeFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        heartFourImage = new Image(heartFourFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        heartFiveImage = new Image(heartFiveFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        heartSixImage = new Image(heartSixFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        heartSevenImage = new Image(heartSevenFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        heartEightImage = new Image(heartEightFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        heartNineImage = new Image(heartNineFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        heartTenImage = new Image(heartTenFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        heartJackImage = new Image(heartJackFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        heartQueenImage = new Image(heartQueenFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        heartKingImage = new Image(heartKingFilePath, cardPixelWidth, cardPixelHeight, true, true),

                        spadeAceImage = new Image(spadeAceFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        spadeTwoImage = new Image(spadeTwoFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        spadeThreeImage = new Image(spadeThreeFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        spadeFourImage = new Image(spadeFourFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        spadeFiveImage = new Image(spadeFiveFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        spadeSixImage = new Image(spadeSixFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        spadeSevenImage = new Image(spadeSevenFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        spadeEightImage = new Image(spadeEightFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        spadeNineImage = new Image(spadeNineFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        spadeTenImage = new Image(spadeTenFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        spadeJackImage = new Image(spadeJackFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        spadeQueenImage = new Image(spadeQueenFilePath, cardPixelWidth, cardPixelHeight, true, true),
                        spadeKingImage = new Image(spadeKingFilePath, cardPixelWidth, cardPixelHeight, true, true);
}
