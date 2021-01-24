regresion_lineal <- function(array_precios,array_banos,array_habitaciones,array_metros) {
  rlm<-lm(array_precios~array_banos+array_habitaciones+array_metros)
  
  intercepto<-coef(rlm)[1]
  Q1<-coef(rlm)[2]
  Q2<-coef(rlm)[3]
  Q3<-coef(rlm)[4]
  
  values <- c(intercepto,Q1,Q2,Q3)
  return(values)
}

predecir_precio <- function(regresion_values,new_values){
  precio<-regresion_values[1]+regresion_values[2]*new_values[1]+regresion_values[3]*new_values[2]+regresion_values[4]+new_values[3]
  
  if(precio<0) {
    precio<-(-1)*precio 
  }
  
  return(precio)
}
