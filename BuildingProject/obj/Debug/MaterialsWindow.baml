   M S B A M L   `   `   `  ����J  FBuildingProject, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null1clr-namespace:BuildingProjectBuildingProject  5   	   %    BuildingProject.MaterialsWindow   5   	   R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35D 9http://schemas.microsoft.com/winfx/2006/xaml/presentation    5   	   R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	   @d2http://schemas.microsoft.com/expression/blend/2008     5   	   Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     5   	   /localclr-namespace:BuildingProject     5   	   -   5          � Title$  MaterialsWindow��5   	   $	��552��6!   $	��880��6.    � WindowStyle$
 None=�6:   .��5	      � .+�5
   
   �  � RowDefinitions 5      �� 5      $��30 �6    �� 5      .+�5      �  � ColumnDefinitions 5      �� 5      �� 5      $��25 �6'   �� 5      $��25 �6'   �� 5      $��5 �6'   .+�5      �� 
�������$��10(�63   $��
Montserrat)�6A   $��0��6Y   $��Center=�6i   $��10,0,0,0q�6�   �� 5      -   5         ��Name$ btnMinimaze��5      $��0q�6.   $��-��6B   
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
   $
��Left=�6   $��	340,7,0,0q�6/   $
��Wrap=�6B   $	��Top=�6V   $��Материалы��6n   $��42��6   $	��196��6�   $��22(�6�   $��Gill Sans Ultra Bold)�6�   ���	#FF694A3D �� TextAlignment$ Center=�6�   6�   �� 5@   
   $	��Top=�6   $
��Left=�6(   $)��#Название материала��6C   $��6,39,0,0q�6`   �� 5A   
   -   5        $��NameMaterialTB��5A      $	��Top=�6(   $
��Left=�6@   $��	10,73,0,0q�6[   $	��103��6n   6z   �� 5C   
   $	��Top=�6   $
��Left=�6(   $��Стоимость��6C   $��
159,39,0,0q�6W   �� 5D   
   -   5        $��PriceTB��5D      $	��Top=�6!   $
��Left=�69   $��
159,73,0,0q�6T   $	��103��6h   6t   �� 5F   
   $	��Top=�6   $
��Left=�6(   $+��%Количество хранения��6C   $��
312,39,0,0q�6a   �� 5G   
   -   5        $��KolvoTB��5G      $	��Top=�6!   $
��Left=�69   $��
312,73,0,0q�6T   $	��103��6h   6t   �� 5I   
   $	��Top=�6   $
��Left=�6(   $'��!Единица измерения��6C   $��
465,39,0,0q�6_   �� 5J   
   -   5        $��EdinizTB��5J      $	��Top=�6"   $
��Left=�6:   $��
465,73,0,0q�6U   $	��103��6i   6u   �� 5L   
   $	��Top=�6   $
��Left=�6(   $��Категория��6C   $��
618,39,0,0q�6W   �� 5M   
   -   5        $��
CategoryTB��5M      $	��Top=�6$   $
��Left=�6<   $��
618,73,0,0q�6W   $	��103��6k   6w   �� 5O   
   $	��Top=�6   $
��Left=�6(   $��Руководитель��6C   $��
756,39,0,0q�6Z   �� 5P   
   -	   5        $��SupervizorTB��5P      $	��Top=�6&   $
��Left=�6>   $��
764,73,0,0q�6Y   $	��103��6m   6y   �� 5R   
   -
   5        $#��Добавить проект��5R      $��370,101,0,0q�6+   $
��Left=�6@   $	��Top=�6[   $	��120��6�   ���	#FF7A5E53
�������$��12(�6�   $��
Montstreet)�6�   $
 Hand��6�   $��25��6�   ��5S      �� 5T      " ��6   .�5U      ��  � �� �	#FF694A3D63   	 5V      P� 5W      
 �@J�IsMouseOver . . �5X      ��  � �� �	#FF694A3D6;   �� 5Y       � �� �	#FA695B5F6;   5Z      5\      ��5^      �� 5_      " ��6"   .R�5`      �� $	��120��6   $��30��6)   $ 20��65   #��z��6G   $��	0,2,0,-14q�6q   ._�5a      �� $��Center=�6+   $��Center=�6F   5b      5c      5e      �� 5f   
   -   5        $%��Удалить материал��5f      $
��Left=�6,   $��
76,481,0,0q�6G   $	��Top=�6[   $	��123��6s   $��21��6�   &   System.Windows.Controls.DataGrid  5g   
   -   5        $��
DGMaterial��5g      $	��Top=�6&   $��Center=�6>   $��	0,147,0,0q�6[   $	��782��6n      AutoGenerateColumns .  $	��293��6�      Columns 5h      0  *System.Windows.Controls.DataGridTextColumn  5i      ,  &System.Windows.Controls.DataGridColumn   Header$) #Название материала��6%      Width5  /System.Windows.Controls.DataGridLengthConverter$ * 6[   1  +System.Windows.Controls.DataGridBoundColumn   Binding 6A   �� *Nname+6[     5j      $ Стоимость��6%   $ * 6V    68   �� *	UnitPrice+6V     5k      $+ %Количество хранения��6%   $ * 6f    6B   �� *QuantityStorage+6f     5l      $' !Единица измерения��6%   $ * 6f    6@   �� *UnitOfMeasurement+6f     5m      $ Категория��6%   $ * 6U    68   �� *	Сategory+6U     5n      $ Руководитель��6%   $ * 6Y    6;   �� *	Providers+6Y   5p      5r      5s      