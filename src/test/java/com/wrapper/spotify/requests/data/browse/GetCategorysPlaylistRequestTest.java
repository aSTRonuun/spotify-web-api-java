package com.wrapper.spotify.requests.data.browse;

import com.wrapper.spotify.ITest;
import com.wrapper.spotify.TestUtil;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class GetCategorysPlaylistRequestTest implements ITest<Paging<PlaylistSimplified>> {
  private final GetCategorysPlaylistsRequest successRequest = SPOTIFY_API.getCategorysPlaylists("id")
          .setHttpManager(
                  TestUtil.MockedHttpManager.returningJson(
                          "requests/data/browse/GetCategorysPlaylistsRequest.json"))
          .build();

  public GetCategorysPlaylistRequestTest() throws Exception {
  }

  @Test
  public void shouldSucceed_sync() throws IOException, SpotifyWebApiException {
    shouldSucceed(successRequest.execute());
  }

  @SuppressWarnings("unchecked")
  @Test
  public void shouldSucceed_async() throws ExecutionException, InterruptedException {
    shouldSucceed((Paging<PlaylistSimplified>) successRequest.executeAsync().get());
  }

  public void shouldSucceed(final Paging<PlaylistSimplified> playlistSimplifiedPaging) {
    assertEquals(
            "https://api.spotify.com/v1/browse/categories/party/playlists?country=BR&offset=0&limit=2",
            playlistSimplifiedPaging.getHref());
    assertEquals(
            2,
            playlistSimplifiedPaging.getItems().length);
    assertEquals(
            2,
            (int) playlistSimplifiedPaging.getLimit());
    assertEquals(
            "https://api.spotify.com/v1/browse/categories/party/playlists?country=BR&offset=2&limit=2",
            playlistSimplifiedPaging.getNext());
    assertEquals(
            0,
            (int) playlistSimplifiedPaging.getOffset());
    assertNull(
            playlistSimplifiedPaging.getPrevious());
    assertEquals(
            86,
            (int) playlistSimplifiedPaging.getTotal());
  }
}
