   M S B A M L   `   `   `  ����J  FBuildingProject, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null1clr-namespace:BuildingProjectBuildingProject  5   	   %    BuildingProject.materialsWorker   5   	   R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35D 9http://schemas.microsoft.com/winfx/2006/xaml/presentation    5   	   R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	   @d2http://schemas.microsoft.com/expression/blend/2008     5   	   Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     5   	   /localclr-namespace:BuildingProject     5   	   -   5          � Title$  materialsWorker��5   	   $	��450��6!   $	��800��6.    � WindowStyle$
 None=�6k   .��5	      � .+�5
   
   �  � RowDefinitions 5      �� 5      $��30 �6    �� 5      .+�5      �  � ColumnDefinitions 5      �� 5      �� 5      $��25 �6'   �� 5      $��25 �6'   �� 5      $��5 �6'   .+�5      �� 
�������$��10(�62   $��
Montserrat)�6@   $��0��6X   $��Center=�6h   $��10,0,0,0q�6�   �� 5      -   5         ��Name$ btnMinimaze��5      $��0q�6.   $��-��6B   
�������$��16(�6a   $��
Montstreet)�6o    � Cursor$
 Hand��6�   $��1��6�   ��5      �� 5       �� 
TargetType" ��6    .�5      ��  �� Property � �� �� Value �#42aaff6;   	 �� Triggers	 5      P� 5   "   
 P� Property
 �@J�IsMouseOver P� Value . . �5   &   ��  � ��
 �����6C   5   #   5       ��5"      �� 5#       �� 
TargetType" ��6*   .R�5$      �� $��18��6%   $��18��60    �� CornerRadius$ 9��6<   #��z��6M   ._�5%   "   �� $��Center=�63   $��Center=�6N   5&      5'      5)      �� 5*      -   5        $ btnClose��5*      $��0q�6,   $��x��6@   
�������$��16(�6_   $��
Montstreet)�6m   $
 Hand��6�   $��2��6�   ��5+      �� 5,      " ��6    .�5-      ��  � �� �#ff00006;   	 5.      P� 5/   "   
 �@J�IsMouseOver . . �50   &   ��  � ��
 �����6C   51   #   53      ��55      �� 56      " ��6*   .R�57      �� $��18��6%   $��18��60   $ 9��6<   #��z��6M   ._�58   "   �� $��Center=�63   $��Center=�6N   59      5:      5<      5=      5>      �� 5?   
   $��16,14,724,382q�6   $��/Image/brickwall.png��6'   $
��Fill=�6E   �� 5@   
   $
��Left=�6   $��
290,23,0,0q�6/   $
��Wrap=�6C   $	��Top=�6W   $��Материалы��6o   $��42��6�   $	��196��6�   $��22(�6�   $��Gill Sans Ultra Bold)�6�   ���	#FF694A3D �� TextAlignment$ Center=�6�   6�   &   System.Windows.Controls.DataGrid  5B   
   -   5        $��
DGMaterial��5B      $	��Top=�6&   $��Center=�6>   $	��782��6[      AutoGenerateColumns .  $	��293��6�   $��0,79,0,0q�6�      Columns 5C      0  *System.Windows.Controls.DataGridTextColumn  5D      ,  &System.Windows.Controls.DataGridColumn   Header$) #Название материала��6%      Width5  /System.Windows.Controls.DataGridLengthConverter$ * 6[   1  +System.Windows.Controls.DataGridBoundColumn   Binding 6A   �� *Nname+6[     5E      $ Стоимость��6%   $ * 6V    68   �� *	UnitPrice+6V     5F      $+ %Количество хранения��6%   $ * 6f    6B   �� *QuantityStorage+6f     5G      $' !Единица измерения��6%   $ * 6f    6@   �� *UnitOfMeasurement+6f     5H      $ Категория��6%   $ * 6U    68   �� *	Сategory+6U     5I      $ Руководитель��6%   $ * 6Y    6;   �� *	Providers+6Y   5K      5M      5N      