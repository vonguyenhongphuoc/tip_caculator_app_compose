package com.devhp.firstcompose.util;


import android.content.Context;
import android.util.Log;

import com.arthenica.ffmpegkit.FFmpegKit;
import com.arthenica.ffmpegkit.FFmpegSession;
import com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback;
import com.arthenica.ffmpegkit.LogCallback;
import com.arthenica.ffmpegkit.ReturnCode;
import com.arthenica.ffmpegkit.SessionState;

import java.util.ArrayList;

public class MergeMedia {
    public static void merge(ArrayList<String> videoPaths) {
        Log.d("MyTag", videoPaths.get(0) + videoPaths.get(1));

        String command2 = "-i " + videoPaths.get(0) + " -i " + videoPaths.get(1) + " -filter_complex [0:a][1:a]amix=inputs=2:duration=first:dropout_transition=2 -vcodec copy /data/user/0/com.devhp.firstcompose/cache/merged.mp4";

        String command = "-i " + videoPaths.get(0) + " -i " + videoPaths.get(1) + " -filter_complex \"[0:v:0] [0:a:0] [1:v:0] [1:a:0] concat=n=2:v=1:a=1 [outv] [outa]\" -map \"[outv]\" -map \"[outa]\" /data/user/0/com.devhp.firstcompose/cache/merged.mp4";
//        String command = "-i " + videoPaths.get(0) + " -i " + videoPaths.get(1) + " -filter_complex concat=n=2:v=1:a=1 [outv] [outa] -map [outv] -map [outa] " + context.getApplicationContext().getCacheDir().getAbsolutePath() + "/merged.mp4";

        FFmpegKit.executeAsync(command2, new FFmpegSessionCompleteCallback() {

            @Override
            public void apply(FFmpegSession session) {
                SessionState state = session.getState();
                ReturnCode returnCode = session.getReturnCode();

                // CALLED WHEN SESSION IS EXECUTED

                Log.d("MyTag", String.format("FFmpeg process exited with state %s and rc %s.%s", state, returnCode, session.getFailStackTrace()));
            }
        }, new LogCallback() {

            @Override
            public void apply(com.arthenica.ffmpegkit.Log log) {

                // CALLED WHEN SESSION PRINTS LOGS

            }
        }, statistics -> {

            // CALLED WHEN SESSION GENERATES STATISTICS

        });

    }
}
