package com.example.yazlab1_proje1;

import com.teamdev.jxbrowser.browser.callback.InjectJsCallback;
import com.teamdev.jxbrowser.js.JsAccessible;
import com.teamdev.jxbrowser.js.JsObject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;


//JwBrowser imports
import com.google.common.base.Charsets;
import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import javafx.scene.layout.BorderPane;
import com.teamdev.jxbrowser.browser.callback.InjectJsCallback.Response;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;
import static java.lang.Math.sqrt;
import static java.lang.String.format;
//JwBrowser imports


import com.google.ortools.Loader;
import com.google.ortools.constraintsolver.Assignment;
import com.google.ortools.constraintsolver.FirstSolutionStrategy;
import com.google.ortools.constraintsolver.RoutingIndexManager;
import com.google.ortools.constraintsolver.RoutingModel;
import com.google.ortools.constraintsolver.RoutingSearchParameters;
import com.google.ortools.constraintsolver.main;

import java.net.HttpURLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Application extends javafx.application.Application {
    private static final Logger log = Logger.getLogger(Application.class.getName());
    public static Connection connection;
    public static Double locationLatToJS, locationLngToJS;
    private static final Logger logger = Logger.getLogger(com.example.yazlab1_proje1.Application.class.getName());
    public static List<Double> distanceMatrixTest = new ArrayList();
    public static List<Integer> Route = new ArrayList<>();

    @Override
    public void start(Stage stage2) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("gui1.fxml"));
            //FXMLLoader fxmlLoader2 = new FXMLLoader(Application.class.getResource("hello-view.fxml"));
            //JxBrowser
            //Licence Key
            System.setProperty("jxbrowser.license.key", "1BNDHFSC1G0LG30NVYOT187A00OAET5MVTA8M6T6ORHI81U1OR6GRTDPFC5E3KUSPZ97OB");

            // Creating and running Chromium engine
            Engine engine = Engine.newInstance(HARDWARE_ACCELERATED);

            Browser browser = engine.newBrowser();
            BrowserView view = BrowserView.newInstance(browser);


            //Inject an instance of the Java object into JavaScript before JavaScript is executed on the loaded web page
            browser.set(InjectJsCallback.class, params -> {
                JsObject window = params.frame().executeJavaScript("window");
                window.putProperty("java", new CargoController());
                return Response.proceed();
            });

            // Loading the required web page
            browser.navigation().loadUrl("C:\\Users\\User\\Documents\\yazlab1_proje1\\src\\main\\resources\\com\\example\\yazlab1_proje1\\map.html");

            // Creating UI component for rendering web content
            // loaded in the given Browser instance
            BorderPane root = new BorderPane();
            root.setCenter(view);
            Scene scene2 = new Scene(root, 1280, 800);
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("GUI 1");
            stage2.setTitle("GUI 2");
            stage2.setScene(scene2);
            stage.setScene(scene);
            stage.show();
            stage2.show();

            stage2.setOnCloseRequest(event -> engine.close());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        connect();
        launch(args);

        //TSP Algorithm

        //my code
        apiConnect();

        Double[] doubleArray = new Double[distanceMatrixTest.size()];
        distanceMatrixTest.toArray(doubleArray);

        int dCounter = 0;
        long[][] longs = new long[(int) sqrt(distanceMatrixTest.size())][(int) sqrt(distanceMatrixTest.size())];
        for (int i = 0; i < (int) sqrt(distanceMatrixTest.size()); i++) {
            for (int j = 0; j < (int) sqrt(distanceMatrixTest.size()); j++) {
                longs[i][j] = doubleArray[dCounter].longValue();
                dCounter++;
            }
        }
        //my code
        Loader.loadNativeLibraries();
        // Instantiate the data problem.
        final long[][] distanceMatrix = longs;/*{
                {0, 2451, 713, 1018, 1631, 1374, 2408, 213, 2571, 875, 1420, 2145, 1972},
                {2451, 0, 1745, 1524, 831, 1240, 959, 2596, 403, 1589, 1374, 357, 579},
                {713, 1745, 0, 355, 920, 803, 1737, 851, 1858, 262, 940, 1453, 1260},
                {1018, 1524, 355, 0, 700, 862, 1395, 1123, 1584, 466, 1056, 1280, 987},
                {1631, 831, 920, 700, 0, 663, 1021, 1769, 949, 796, 879, 586, 371},
                {1374, 1240, 803, 862, 663, 0, 1681, 1551, 1765, 547, 225, 887, 999},
                {2408, 959, 1737, 1395, 1021, 1681, 0, 2493, 678, 1724, 1891, 1114, 701},
                {213, 2596, 851, 1123, 1769, 1551, 2493, 0, 2699, 1038, 1605, 2300, 2099},
                {2571, 403, 1858, 1584, 949, 1765, 678, 2699, 0, 1744, 1645, 653, 600},
                {875, 1589, 262, 466, 796, 547, 1724, 1038, 1744, 0, 679, 1272, 1162},
                {1420, 1374, 940, 1056, 879, 225, 1891, 1605, 1645, 679, 0, 1017, 1200},
                {2145, 357, 1453, 1280, 586, 887, 1114, 2300, 653, 1272, 1017, 0, 504},
                {1972, 579, 1260, 987, 371, 999, 701, 2099, 600, 1162, 1200, 504, 0},
        };*/
        final int vehicleNumber = 1;
        final int depot = 0;


        // Create Routing Index Manager
        RoutingIndexManager manager =
                new RoutingIndexManager(distanceMatrix.length, vehicleNumber, depot);

        // Create Routing Model.
        RoutingModel routing = new RoutingModel(manager);

        // Create and register a transit callback.
        final int transitCallbackIndex =
                routing.registerTransitCallback((long fromIndex, long toIndex) -> {
                    // Convert from routing variable Index to user NodeIndex.
                    int fromNode = manager.indexToNode(fromIndex);
                    int toNode = manager.indexToNode(toIndex);
                    return distanceMatrix[fromNode][toNode];
                });

        // Define cost of each arc.
        routing.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);

        // Setting first solution heuristic.
        RoutingSearchParameters searchParameters =
                main.defaultRoutingSearchParameters()
                        .toBuilder()
                        .setFirstSolutionStrategy(FirstSolutionStrategy.Value.PATH_CHEAPEST_ARC)
                        .build();

        // Solve the problem.
        Assignment solution = routing.solveWithParameters(searchParameters);

        // Print solution on console.
        printSolution(routing, manager, solution);

        for (Integer i:
             Route) {
            System.out.print(i);
        }
    }

    public static void apiConnect() {

        //API
        try {
            URL url = new URL("http://localhost:8080/");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Getting the response code
            int responsecode = conn.getResponseCode();

            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {

                String inline = "";
                Scanner scanner = new Scanner(url.openStream());

                //Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }

                //Close the scanner
                scanner.close();

                System.out.println(inline);
                //Using the JSON simple library parse the string into a json object
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(inline);
                //JSONObject jo = (JSONObject) obj;
                //System.out.println((String) jo.get("name"));
                JSONArray array = (JSONArray) obj;
                System.out.println("The 1st element of array");
                System.out.println(array.get(0));

                String[] s = null;
                for (int i = 0; i < array.size(); i++) {
                    JSONObject new_obj = (JSONObject) array.get(i);
                    System.out.println(new_obj.get("name").toString());
                    s = new_obj.get("name").toString().replace("[", "").
                            replace("]", "").replace(" km", "").replace("\"", "").replace(" m", "").split(",");


                }
                int sCounter = 0;
                int sqrt = (int) sqrt(s.length);
                for (String str : s) {
                    System.out.println(str);
                }
                for (int i = 0; i < sqrt; i++) {
                    for (int j = 0; j < sqrt; j++) {
                        if (i == j)
                            distanceMatrixTest.add((double) 0);
                        else
                            distanceMatrixTest.add(Double.parseDouble(s[sCounter]));
                        sCounter++;
                    }
                }
                System.out.println("distanceMatrixTest");
                int jCounter = 0;
                for (int i = 0; i < sqrt; i++) {
                    for (int j = 0; j < sqrt; j++) {
                        //Convert to meters
                        distanceMatrixTest.set(jCounter, distanceMatrixTest.get(jCounter) * 1000);
                        System.out.print(distanceMatrixTest.get(jCounter) + " ");
                        jCounter++;
                    }
                    System.out.println();
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        //API

    }

    public static Connection connect() {
        String connectionUrl = "jdbc:sqlserver://yazlab.database.windows.net:1433;database=yazlab1_proje1;user=arman@yazlab;password=D9]E4gQveoyELcX^!Xgl;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        try {
            connection = DriverManager.getConnection(connectionUrl);
            log.info("Connection established");
            /*Statement myStatement = connection.createStatement();
            myStatement.executeQuery("select * from users");
            while(myRs.next()){
                System.out.println(myRs.getString("username"));
                System.out.println(myRs.getString("password"));
            }*/
            return connection;
        } catch (Exception e) {
            log.info("No connection established. Error message: " + e);
        }

        return null;
    }

    /**
     * The object observing DOM changes.
     *
     * <p>The class and methods that are invoked from JavaScript code must be public.
     */
    public static class JavaObject {

        @SuppressWarnings("unused") // invoked by callback processing code.
        @JsAccessible
        public void onDomChanged(String innerHtml) {
            System.out.println("DOM node changed: " + innerHtml);
        }
    }

    /// @brief Print the solution.
    static void printSolution(
            RoutingModel routing, RoutingIndexManager manager, Assignment solution) throws IOException {
        // Solution cost.
        logger.info("Objective: " + solution.objectiveValue() + "miles");
        // Inspect solution.
        logger.info("Route:");
        long routeDistance = 0;
        String route = "";
        long index = routing.start(0);
        while (!routing.isEnd(index)) {
            route += manager.indexToNode(index) + " -> ";

            Route.add(manager.indexToNode(index));

            long previousIndex = index;
            index = solution.value(routing.nextVar(index));
            routeDistance += routing.getArcCostForVehicle(previousIndex, index, 0);
        }
        route += manager.indexToNode(routing.end(0));
        logger.info(route);
        logger.info("Route distance: " + routeDistance + "meters");
        apiPost();
    }

    static void apiPost(){
        try {
            // Construct manually a JSON object in Java, for testing purposes an object with an object
            JSONObject data = new JSONObject();
            JSONObject auth = new JSONObject();
            //auth.put("login", "1234567890");
            String formattedRoute="";
            for(Integer i:Route){
                formattedRoute += i + " ";
            }
            data.put("id","01");
            data.put("name","");
            data.put("route",formattedRoute);

            // URL and parameters for the connection, This particulary returns the information passed
            URL url = new URL("http://localhost:8080/");
            HttpURLConnection httpConnection  = (HttpURLConnection) url.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json");
            httpConnection.setRequestProperty("Accept", "application/json");
            // Not required
            // urlConnection.setRequestProperty("Content-Length", String.valueOf(input.getBytes().length));

            // Writes the JSON parsed as string to the connection
            DataOutputStream wr = new DataOutputStream(httpConnection.getOutputStream());
            wr.write(data.toString().getBytes());
            Integer responseCode = httpConnection.getResponseCode();

            BufferedReader bufferedReader;

            // Creates a reader buffer
            if (responseCode > 199 && responseCode < 300) {
                bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getErrorStream()));
            }

            // To receive the response
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            bufferedReader.close();

            // Prints the response
            System.out.println(content.toString());

        } catch (Exception e) {
            System.out.println("Error Message");
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }    }
}