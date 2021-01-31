<?php 
if(isset($_POST['post'])){
    $busqueda = $_POST['busqueda'];
    $euroUrl = "";
    $token2 = strtok($busqueda, " ");
    while ($token2 !== false){
        $euroUrl = $euroUrl.$token2."+";
        $token2 = strtok(" ");
    }
    $url = 'https://api.datamuse.com/words?ml='.$euroUrl."&max=1";
    $options = array(
        'http' => array(
            'header'  => "Accept-language: en\r\n" . "Content-type: application/x-www-form-urlencoded\r\n",
            'method'  => 'GET'
        )
    );
    

    $context  = stream_context_create($options);
    $result = file_get_contents($url, false, $context);

    $API_key= "sTssGo84K";

    $array = array_values(json_decode($result, true));
    $euroUrl .= $array[0]['word'];
    $europeanaBusqueda = "https://www.europeana.eu/api/v2/search.json?wskey=".$API_key."&query=".$euroUrl."&sort=score";
    $plosBusqueda = "http://api.plos.org/search?q=".$euroUrl."&fl=id,title,score,abstract";
    $context  = stream_context_create($options);
    $resultadoEuropeana = file_get_contents($europeanaBusqueda, false, $context);
    $resultadosPlos = file_get_contents($plosBusqueda, false, $context);
    $arrayResultadosEuropeanaOrdenados = array_values(json_decode($resultadoEuropeana, true))[5];
    $arrayResultadosPlos = array_values(json_decode($resultadosPlos, true))[0]["docs"];

    $price = array_column($arrayResultadosPlos, 'score');
    array_multisort($price, SORT_DESC, $arrayResultadosPlos);
    $maximoEuropeana = $arrayResultadosEuropeanaOrdenados[0]["score"];
    $maximoPlos = $arrayResultadosPlos[0]["score"];

    $numeroResultadosEuropeana = count($arrayResultadosEuropeanaOrdenados);
    $numeroResultadosPlos = count($arrayResultadosPlos);
    $normalizer = $maximoPlos/$maximoEuropeana;

    for ($i=0; $i < $numeroResultadosEuropeana; $i++) {
        $arrayResultadosEuropeanaOrdenados[$i]['score'] = $arrayResultadosEuropeanaOrdenados[$i]['score']*$normalizer;
    }

    for ($i=0; $i < $numeroResultadosPlos; $i++) {
        $arrayResultadosPlos[$i]['score'] = $arrayResultadosPlos[$i]['score']*$normalizer;
    }



    $todosLosResultados = array_merge($arrayResultadosEuropeanaOrdenados, $arrayResultadosPlos);
    $ordenador = array_column($todosLosResultados, 'score');
    array_multisort($ordenador, SORT_DESC, $todosLosResultados);

    //var_dump($todosLosResultados);
    if ($result === FALSE) {  
        echo "No se pudo completar la solicitud";
    }else{ 
        //var_dump($arrayResultadosPlos);
        //var_dump($arrayResultadosEuropeana);
    }

    display_results($todosLosResultados);
}


?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <h2>BUSCAR</h2>
    <form method="post">
        <input type="text" name="busqueda" />
        <input type="submit" name="post" />

    </form>
</body>
</html>
<?php

function display_results($results) {
    //$table = "<table><thead><tr><th>Nombre</th> <th> preview</th> <th> Peso</th></tr></thead><tbody>";
    foreach($results as $result) {
        if(isset($result["country"])) {

            //echo var_dump($result)."<br>";
            echo "<a href=".$result["guid"]."><h2>".$result["title"][0]."</h2></a>";
        } else {
            echo "<a href=".$result["id"]."><h2>".$result["title"]."</h2></a>";
            //echo var_dump($result)."<br><div height='30px'>";
        }
    }

    //$table .= "</tbody></table>";
}

?>