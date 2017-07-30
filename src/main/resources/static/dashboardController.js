angular.module("serpring")
  .controller("dashboardController", function($scope, $http) {
    
    // LOGIN
    $scope.userLogin = {};
    $scope.userSubscribe = {};
    $scope.currentSession = {};
    // SEARCH
    $scope.searchInput;
    $scope.serieSearchObject = {};
    $scope.searchRequest = [];
    // PROFILE
    $scope.profileWatchlist = [];
    $scope.profileWatching = [];
    // STATUS
    $scope.info = { status: "info", text: "Você precisa estar logado para adicionar séries" };
    // CONSTANTS IMDB_API
    const IMDB_API_ARGS = "&type=series";
    const IMDB_API_APIKEY = "&apikey=93330d3c&";
    const IMDB_API_BASEURL = "https://omdbapi.com/?s=";
    const IMDB_API_BASEURLID = "https://omdbapi.com/?i=";
    // CONSTANTS SERPRING_API
    const SERPRING_API_BASEURL = "http://localhost:8080/serpring/";
    const SERPRING_API_LOGIN = "http://localhost:8080/serpring/login";
    const SERPRING_API_LOGOUT = "http://localhost:8080/serpring/logout";
    const SERPRING_API_PROFILE = "http://localhost:8080/serpring/profile";
    const SERPRING_API_PROFILE_SEARCH = "http://localhost:8080/serpring/profile/search";
    const SERPRING_API_SERIES = "http://localhost:8080/serpring/profile/series";
    const SERPRING_API_WATCHLIST = "http://localhost:8080/serpring/profile/watchlist";

    // SESSION BOOT
    var loadSession = function() {
      $http({method: 'GET', url: SERPRING_API_LOGIN})
      .then(function(response) {
        $scope.currentSession = response.data;
        $scope.statusSuccess("Bem vindo " + $scope.currentSession.name);
        return response.status;
      })
      .catch(function(fallback) {
        $scope.statusInfo("Você precisa estar logado para adicionar séries");
      });
    };

    var updateAllSeriesFromWatching = function() {
      $http({method: 'GET', url: SERPRING_API_SERIES})
      .then(function(response) {
        $scope.profileWatching = response.data;
        console.log($scope.profileWatching);
        console.log(response);
      }, function(error) {
        console.log(error);
      });
    };

    var updateAllSeriesFromWatchlist = function() {
      $http({method: 'GET', url: SERPRING_API_WATCHLIST})
      .then(function(response) {
        $scope.profileWatchlist = response.data;
        console.log($scope.profileWatchlist);
        console.log(response);
      }, function(error) {
        console.log(error);
      });
    };

    loadSession();
    updateAllSeriesFromWatching();
    updateAllSeriesFromWatchlist();

    // INFO STATUS
    $scope.statusSuccess = function(message) {
      $scope.info.status = "success";
      $scope.info.text = message;
    };

    $scope.statusInfo = function(message) {
      $scope.info.status = "info";
      $scope.info.text = message;
    };

    $scope.statusWarning = function(message) {
      $scope.info.status = "warning";
      $scope.info.text = message;
    };

    $scope.statusDanger = function(message) {
      $scope.info.status = "danger";
      $scope.info.text = message;
    };    

    // PROFILE SUB-CONTROLLER
    $scope.addProfile = function(profile) {
      $http({method: 'POST', url: SERPRING_API_PROFILE, data: $scope.userSubscribe})
      .then(function(response) {
        $scope.statusSuccess("Usuário cadastrado com sucesso");
        $scope.userSubscribe = {};
        console.log(response);
      }, function(error) {
        $scope.statusDanger("Cadastro não autorizado, por favor confira seus dados");
        console.log(error);
      })
    };

    // LOGIN SUB-CONTROLLER
    $scope.login = function() {
      $http({method: 'POST', url: SERPRING_API_LOGIN, data: $scope.userLogin})
      .then(function(response) {
        $scope.currentSession = response.data;
        $scope.userLogin = {};
        $scope.statusSuccess("Bem vindo " + $scope.currentSession.name);
        loadSession();
        updateAllSeriesFromWatching();
        updateAllSeriesFromWatchlist();
        console.log(response);
      }, function(error) {
        $scope.statusDanger("Login não autorizado, por favor confira seus dados");
        console.log(error);
      })
    };

    $scope.logout = function(user) {
      $http({method: 'POST', url: SERPRING_API_LOGOUT, data: $scope.currentSession})
      .then(function(response) {
        $scope.statusSuccess("Até mais " + $scope.currentSession.name);
        $scope.currentSession = {};
        loadSession();
        updateAllSeriesFromWatching();
        updateAllSeriesFromWatchlist();
        console.log(response);
      }, function(response) {
        console.log(response);
        $scope.statusDanger("Logout não autorizado");
      })
    };

    // WATCHING SUB-CONTROLLER
    $scope.addSerieToProfile = function(serie) {
      var promise = $http({method: 'GET', url: IMDB_API_BASEURLID + serie.imdbID + IMDB_API_APIKEY})
      .then(function (response) {
        
        // build serie to api rest
        var newSerie = {
          "title": response.data.Title,
          "year": response.data.Year,
          "released": response.data.Released,
          "actors": response.data.Actors,
          "plot": response.data.Plot,
          "awards": response.data.Awards,
          "poster": response.data.Poster,
          "imdbRating": response.data.imdbRating,
          "imdbID": response.data.imdbID,
        }

        // send serie to database
        $http({method: 'POST', url: SERPRING_API_SERIES, data: newSerie})
        .then(function(response) {
          updateAllSeriesFromWatching();
          $scope.statusSuccess("A série foi adicionada com sucesso");
          console.log(response);
        }, function(error) {
        $scope.statusDanger("Esta série já está adicionada em seu perfil!");
          console.log(error);
        });

      }, function error (error) {
          console.log(error);
      });
    };

    $scope.removeSerieFromProfile = function (serie) {
      if (confirm('Você tem certeza que deseja remover "' + serie.title + '" do seu perfil?') === true) {
        $http({method: 'DELETE', url: SERPRING_API_SERIES + "/" + serie.imdbID, data: serie.imdbID})
        .then(function(response) {
          updateAllSeriesFromWatching();
          $scope.statusSuccess("A série foi removida com sucesso");
          console.log(response);
        }, function(error) {
        $scope.statusDanger("Erro interno ao tentar remover a série!");
          console.log(error);
        });
      }
    };

    $scope.addRatingToProfile = function (serie, rating) {
      serie.myRating = rating;
      $http({method: 'PUT', url: SERPRING_API_SERIES, data: serie})
        .then(function(response) {
          $scope.statusSuccess("Nota adicionada com sucesso");
          console.log(response);
        }, function(error) {
        $scope.statusDanger("Erro interno ao tentar atribuir nota nessa série!");
          console.log(error);
        });
    };

    $scope.addLastEpisodeToProfile = function (serie, episode) {
      serie.lastEpisodeWatched = episode;
      $http({method: 'PUT', url: SERPRING_API_SERIES, data: serie})
        .then(function(response) {
          $scope.statusSuccess("Último episódio adicionado com sucesso");
          console.log(response);
        }, function(error) {
        $scope.statusDanger("Erro interno ao tentar adicionar último episódio");
          console.log(error);
        });
    };

    // WATCHLIST SUB-CONTROLLER
    $scope.addSerieToWatchlist = function(serie) {
      var promise = $http({method: 'GET', url: IMDB_API_BASEURLID + serie.imdbID + IMDB_API_APIKEY})
      .then(function (response) {
        
        // build serie to api rest
        var newSerie = {
          "title": response.data.Title,
          "year": response.data.Year,
          "released": response.data.Released,
          "actors": response.data.Actors,
          "plot": response.data.Plot,
          "awards": response.data.Awards,
          "poster": response.data.Poster,
          "imdbRating": response.data.imdbRating,
          "imdbID": response.data.imdbID,
        }

        // serie to database
        $http({method: 'POST', url: SERPRING_API_WATCHLIST, data: newSerie})
        .then(function(response) {
          updateAllSeriesFromWatchlist();
          $scope.statusSuccess("A série foi adicionada com sucesso");
          console.log(response);
        }, function(error) {
        $scope.statusDanger("Esta série já está adicionada em sua lista de desejos!");
          console.log(error);
        });

      }, function error (error) {
          console.log(error);
      });
    };

    $scope.removeSerieFromWatchlist = function (serie) {
      if (confirm('Você tem certeza que deseja remover "' + serie.title + '" da sua lista de desejos?') === true) {
        $http({method: 'DELETE', url: SERPRING_API_WATCHLIST + "/" + serie.imdbID, data: serie.imdbID})
        .then(function(response) {
          updateAllSeriesFromWatchlist();
          $scope.statusSuccess("A série foi removida com sucesso");
          console.log(response);
        }, function(error) {
        $scope.statusDanger("Erro interno ao tentar remover a série!");
          console.log(error);
        });
      }
    };

    $scope.moveSerieFromWatching = function (serie) {
      if (confirm('Você tem certeza que deseja mover "' + serie.title + '" para seu perfil?') === true) {
        $scope.addSerieToProfile(serie);
        $scope.profileWatchlist.splice($scope.profileWatchlist.indexOf(serie), 1);
      }
    };

    // SEARCH SUB-CONTROLLER IMDB_API
    $scope.searchSerieRequest = function () {
      if ($scope.searchInput.length > 0) {
        return $http
          .get(IMDB_API_BASEURL + $scope.searchInput + IMDB_API_ARGS + IMDB_API_APIKEY)
          .then(function (response) {
            $scope.searchRequest = response.data.Search;
            console.log(response.data.Search);
          }, function error (error) {
            console.log(error);
          });
      }
    };

});
