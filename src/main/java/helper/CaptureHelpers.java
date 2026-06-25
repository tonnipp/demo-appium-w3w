package helper;

import helper.properties.PropertiesFile;
import org.monte.media.Format;
import org.monte.media.FormatKeys;
import org.monte.media.Registry;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import utils.logs.Log;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.monte.media.AudioFormatKeys.*;
import static org.monte.media.FormatKeys.MediaType;
import static org.monte.media.VideoFormatKeys.*;

public class CaptureHelpers extends ScreenRecorder {

    private static final String PROJECT_PATH =
            System.getProperty("user.dir") + "/";

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");

    private static ScreenRecorder screenRecorder;

    private final String fileName;

    /**
     * Constructor for screen recorder.
     */
    public CaptureHelpers(GraphicsConfiguration config,
                          Rectangle captureArea,
                          Format fileFormat,
                          Format screenFormat,
                          Format mouseFormat,
                          Format audioFormat,
                          File movieFolder,
                          String fileName)
            throws IOException, AWTException {

        super(config,
                captureArea,
                fileFormat,
                screenFormat,
                mouseFormat,
                audioFormat,
                movieFolder);

        this.fileName = fileName;
    }

    /**
     * Capture screenshot.
     */
    public static void captureScreenshot(WebDriver driver,
                                         String screenName) {

        try {

            TakesScreenshot takesScreenshot =
                    (TakesScreenshot) driver;

            File source =
                    takesScreenshot.getScreenshotAs(OutputType.FILE);

            String screenshotFolder =
                    PROJECT_PATH +
                            PropertiesFile.get("exportCapturePath");

            File folder = new File(screenshotFolder);

            if (!folder.exists()) {
                folder.mkdirs();
            }

            String screenshotName =
                    screenName + "_"
                            + LocalDateTime.now().format(DATE_FORMAT)
                            + ".png";

            File destination =
                    new File(folder, screenshotName);

            FileHandler.copy(source, destination);

            Log.info("Screenshot saved: "
                    + destination.getAbsolutePath());

        } catch (Exception e) {

            Log.error("Failed to capture screenshot: "
                    + e.getMessage());

        }
    }

    /**
     * Customize movie file name.
     */
    @Override
    protected File createMovieFile(Format fileFormat)
            throws IOException {

        if (!movieFolder.exists()) {

            movieFolder.mkdirs();

        } else if (!movieFolder.isDirectory()) {

            throw new IOException(
                    "\"" + movieFolder + "\" is not a directory.");
        }

        return new File(
                movieFolder,
                fileName
                        + "-"
                        + LocalDateTime.now().format(DATE_FORMAT)
                        + "."
                        + Registry.getInstance().getExtension(fileFormat));
    }

    /**
     * Start video recording.
     */
    public static void startRecord(String methodName)
            throws Exception {

        String videoFolder =
                PROJECT_PATH
                        + PropertiesFile.get("exportVideoPath")
                        + "/"
                        + methodName;

        File folder = new File(videoFolder);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        Dimension screenSize =
                Toolkit.getDefaultToolkit().getScreenSize();

        Rectangle captureArea =
                new Rectangle(
                        0,
                        0,
                        screenSize.width,
                        screenSize.height);

        GraphicsConfiguration configuration =
                GraphicsEnvironment
                        .getLocalGraphicsEnvironment()
                        .getDefaultScreenDevice()
                        .getDefaultConfiguration();

        screenRecorder =
                new CaptureHelpers(
                        configuration,
                        captureArea,

                        new Format(
                                FormatKeys.MediaTypeKey,
                                MediaType.FILE,
                                FormatKeys.MimeTypeKey,
                                MIME_AVI),

                        new Format(
                                FormatKeys.MediaTypeKey,
                                MediaType.VIDEO,
                                EncodingKey,
                                ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                                CompressorNameKey,
                                ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                                DepthKey,
                                24,
                                FrameRateKey,
                                Rational.valueOf(15),
                                QualityKey,
                                1.0f,
                                KeyFrameIntervalKey,
                                15 * 60),

                        new Format(
                                FormatKeys.MediaTypeKey,
                                MediaType.VIDEO,
                                EncodingKey,
                                "black",
                                FrameRateKey,
                                Rational.valueOf(30)),

                        null,
                        folder,
                        methodName);

        screenRecorder.start();

        Log.info("Video recording started.");
    }

    /**
     * Stop video recording.
     */
    public static void stopRecord() throws Exception {

        if (screenRecorder != null) {

            screenRecorder.stop();

            Log.info("Video recording stopped.");
        }
    }
}