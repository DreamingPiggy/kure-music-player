package com.kure.musicplayer.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.kure.musicplayer.kMP;

/**
 * Asynchronous service that will communicate with the
 * MusicService and scrobble songs to Last.fm through
 * installed applications.
 *
 * - It listens to Broadcasts from MusicService, like when
 *   the music started, paused, has changed...
 * - Then sends that information to other applications that
 *   directly communicates with Last.fm.
 *   There's a handful of them, check out on Settings.
 *
 * Thanks:
 * - Vogella, for the awesome tutorial on Services.
 *   http://www.vogella.com/tutorials/AndroidServices/article.html
 *
 */
public class MusicScrobblerService extends Service {

	/**
	 * Used for Services that want to bind to a specific
	 * Activity or such. Since this service is not bound
	 * to anything, let's just ignore this function.
	 */
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Service is triggered to (re)start.
	 *
	 * @note Might be called several times.
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		if (intent == null) {
			// We just got restarted after Android killed us

		}
		else {
			// This service is being explicitly started

		}

		// This makes sure this service will be restarted
		// when Android kills it.
		// When it does, the `intent` will be `null`.
		return Service.START_STICKY;
	};

	/**
	 * Service just got created.
	 */
	@Override
	public void onCreate() {

		// Registering the BroadcastReceiver to listen
		// to the MusicService.
		LocalBroadcastManager
		.getInstance(getApplicationContext())
		.registerReceiver(musicServiceBroadcastReceiver, new IntentFilter(MusicService.BROADCAST_EVENT_NAME));

		super.onCreate();
	};

	@Override
	public void onDestroy() {

		// Unregistering the BroadcastReceiver
		LocalBroadcastManager
		.getInstance(getApplicationContext())
		.unregisterReceiver(musicServiceBroadcastReceiver);

		super.onDestroy();
	};

	/**
	 * The thing that will keep an eye on LocalBroadcasts
	 * for the MusicService.
	 */
	BroadcastReceiver musicServiceBroadcastReceiver = new BroadcastReceiver() {

		/**
		 * What it'll do when receiving a message from the
		 * MusicService?
		 */
		@Override
		public void onReceive(Context context, Intent intent) {

			String action  = intent.getStringExtra(MusicService.BROADCAST_EXTRA_ACTION);
			Long   song_id = intent.getLongExtra(MusicService.BROADCAST_EXTRA_SONG_ID, -1);

			if (action.equals(MusicService.BROADCAST_EXTRA_PLAYING)) {
				Toast.makeText(getApplicationContext(), "Playing", Toast.LENGTH_SHORT).show();
			}
			else if (action.equals(MusicService.BROADCAST_EXTRA_PAUSED)) {
				Toast.makeText(getApplicationContext(), "Paused", Toast.LENGTH_SHORT).show();
			}
			else if (action.equals(MusicService.BROADCAST_EXTRA_COMPLETED)) {
				Toast.makeText(getApplicationContext(), "Completed", Toast.LENGTH_SHORT).show();
			}
			else if (action.equals(MusicService.BROADCAST_EXTRA_UNPAUSED)) {
				Toast.makeText(getApplicationContext(), "Unpaused", Toast.LENGTH_SHORT).show();
			}
			else if (action.equals(MusicService.BROADCAST_EXTRA_SKIP_NEXT)) {
				Toast.makeText(getApplicationContext(), "Next", Toast.LENGTH_SHORT).show();
			}
			else if (action.equals(MusicService.BROADCAST_EXTRA_SKIP_PREVIOUS)) {
				Toast.makeText(getApplicationContext(), "Previous", Toast.LENGTH_SHORT).show();
			}
			else
				return;

			if (song_id != -1)
				Toast.makeText(getApplicationContext(), kMP.songs.getSongById(song_id).getTitle(), Toast.LENGTH_SHORT).show();

		}
	};

	/**
	 * Last.fm support!
	 *
	 * We'll send our current song to ScrobbleDroid ONLY IF
	 * the preference for it is enabled.
	 *
	 * This needs to be called as often as possible - when pausing,
	 * resuming, when the track is going to be changed, when the
	 * track is changed...
	 *
	 * @note To avoid concurrency issues, make sure to clal
	 *       this method only when the music player is prepared!
	 * @see onPrepared()
	 */
	private void scrobbleCurrentSong(boolean isPlaying) {
/*
		// Only scrobbling if the user lets us
		if (! kMP.settings.get("lastfm", false))
			return;

		Intent scrobble = new Intent("net.jjc1138.android.scrobbler.action.MUSIC_STATUS");

		scrobble.putExtra("playing", isPlaying);
		scrobble.putExtra("id", getCurrentSongId());

		sendBroadcast(scrobble);
*/	}
}
