package com.fre.recorder;

import java.awt.*;
import java.io.File;
import java.nio.ByteOrder;
import org.monte.media.Format;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;

import static org.monte.media.AudioFormatKeys.*;
import static org.monte.media.AudioFormatKeys.MIME_QUICKTIME;
import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.MediaType;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.*;
import static org.monte.screenrecorder.ScreenRecorder.*;


/**
 * Created by fcapponi on 10/04/17.
 */
public class Main {

  private ScreenRecorder screenRecorder;

  public static void main(String[] args) throws Exception {
    new Main().startRecording();
  }

  public void startRecording() throws Exception {
    GraphicsConfiguration gc =
        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    this.screenRecorder = new ScreenRecorder(gc,// the file format
        new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_QUICKTIME),
        //
        // the output format for screen capture
        new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_QUICKTIME_ANIMATION, CompressorNameKey,
            COMPRESSOR_NAME_QUICKTIME_ANIMATION, DepthKey, 24, FrameRateKey, new Rational(5, 1)),
        //
        // the output format for mouse capture
        new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_BLACK_CURSOR, FrameRateKey,
            new Rational(5, 1)),
        //
        // the output format for audio capture
        new Format(MediaTypeKey, MediaType.AUDIO, EncodingKey, ENCODING_QUICKTIME_TWOS_PCM, FrameRateKey,
            new Rational(48000, 1), SampleSizeInBitsKey, 16, ChannelsKey, 1, SampleRateKey, new Rational(48000, 1),
            SignedKey, true, ByteOrderKey, ByteOrder.BIG_ENDIAN));

    this.screenRecorder.start();
    Thread.sleep(10000);
    this.screenRecorder.stop();
    for (File file : this.screenRecorder.getCreatedMovieFiles()) {
      System.out.println(file);
    }
  }

  public void stopRecording() throws Exception {
    this.screenRecorder.stop();
  }
}
