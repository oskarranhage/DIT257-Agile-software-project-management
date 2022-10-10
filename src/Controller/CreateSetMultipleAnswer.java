<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.Button?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.Pane?>
<?import javafx.scene.shape.Line?>
<?import javafx.scene.text.Text?>

<fx:root maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="285.0" prefWidth="653.0" type="AnchorPane" xmlns="http://javafx.com/javafx/17" xmlns:fx="http://javafx.com/fxml/1">
   <Pane prefHeight="88.0" prefWidth="652.0" style="-fx-background-color: #C8FDDD;">
      <children>
         <ImageView fitHeight="32.0" fitWidth="34.0" layoutX="593.0" layoutY="21.0">
            <image>
               <Image url="@../../../../../Downloads/delete-button.png" />
            </image>
         </ImageView>
         <ImageView fitHeight="32.0" fitWidth="34.0" layoutX="593.0" layoutY="21.0">
            <image>
               <Image url="@../../../../../Downloads/correct.png" />
            </image>
         </ImageView>
      </children>
   </Pane>
   <Text layoutX="53.0" layoutY="69.0" strokeType="OUTSIDE" strokeWidth="0.0" text="Term" />
   <Text layoutX="342.0" layoutY="69.0" strokeType="OUTSIDE" strokeWidth="0.0" text="Definition" />
   <Line endX="233.0" endY="36.166664123535156" layoutX="45.0" layoutY="18.0" startX="8.0" startY="36.166664123535156" />
   <Line endX="233.0" endY="36.166664123535156" layoutX="334.0" layoutY="17.0" startX="8.0" startY="36.166664123535156" />
   <TextField fx:id="defTextC" layoutX="342.0" layoutY="19.0" prefHeight="25.0" prefWidth="226.0" text="Deffo" />
   <TextField fx:id="termTextC" layoutX="53.0" layoutY="19.0" prefHeight="25.0" prefWidth="226.0" text="Term" />
   <Pane layoutY="89.0" prefHeight="195.0" prefWidth="652.0" style="-fx-background-color: #FBC4BD;">
      <children>
         <Button layoutX="607.0" layoutY="153.0" mnemonicParsing="false" onMouseClicked="#removeCreateListItem" text="X" />
         <Line endX="584.3333129882812" endY="36.16667938232422" layoutX="46.0" layoutY="26.0" startX="-25.333343505859375" startY="36.16667175292969" />
         <TextField fx:id="defTextC1" layoutX="341.0" layoutY="20.0" prefHeight="25.0" prefWidth="226.0" text="Deffo" />
         <TextField fx:id="defTextC11" layoutX="341.0" layoutY="79.0" prefHeight="25.0" prefWidth="226.0" text="Deffo" />
         <TextField fx:id="defTextC12" layoutX="341.0" layoutY="141.0" prefHeight="25.0" prefWidth="226.0" text="Deffo" />
         <Line endX="584.3333129882812" endY="36.16667938232422" layoutX="46.0" layoutY="88.0" startX="-25.333343505859375" startY="36.16667175292969" />
      </children>
   </Pane>
   <Line endX="584.3333129882812" endY="36.16667938232422" layoutX="45.0" layoutY="53.0" startX="-25.0" startY="36.16667938232422" />
</fx:root>